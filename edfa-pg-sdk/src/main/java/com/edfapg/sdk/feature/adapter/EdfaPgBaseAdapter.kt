/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.adapter

import androidx.viewbinding.BuildConfig
import com.edfapg.sdk.core.ENABLE_DEBUG
import com.edfapg.sdk.core.EdfaPgCredential
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.Exception

/**
 * The base EdfaPg API Adapter.
 *
 * The [Retrofit] and [OkHttpClient] used for the async request.
 * The [Gson] serialize/deserialize the request/response bodies.
 * The [HttpLoggingInterceptor] logging all operation. Only in debug.
 * @see EdfaPgCallback
 * @see com.edfapg.sdk.feature.deserializer.EdfaPgBaseDeserializer
 *
 * @param Service the operation by the [com.edfapg.sdk.model.api.EdfaPgAction].
 */
abstract class EdfaPgBaseAdapter<Service> {

    companion object {
        /**
         * Date format pattern in the Payment Platform.
         * Format: yyyy-MM-dd, e.g. 1970-02-17
         */
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    /**
     * The [Service] instance.
     */
    protected val service: Service
    private val gson: Gson

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG || ENABLE_DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        configureOkHttpClient(okHttpClientBuilder)

        val gsonBuilder = GsonBuilder()
        gsonBuilder.setPrettyPrinting()
        gsonBuilder.setDateFormat(DATE_FORMAT)
        configureGson(gsonBuilder)
        gson = gsonBuilder.create()

        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(EdfaPgUtil.validateBaseUrl(EdfaPgCredential.paymentUrl()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClientBuilder.build())
        configureRetrofit(retrofitBuilder)

        service = retrofitBuilder.build().create(provideServiceClass())
    }

    /**
     * Provides the [Service] class.
     *
     * @return class by [Service].
     */
    protected abstract fun provideServiceClass(): Class<Service>

    /**
     * Configures the [OkHttpClient.Builder].
     *
     * @param builder the [OkHttpClient.Builder].
     */
    protected open fun configureOkHttpClient(builder: OkHttpClient.Builder) = Unit

    /**
     * Configures the [GsonBuilder].
     *
     * @param builder the [GsonBuilder].
     */
    protected open fun configureGson(builder: GsonBuilder) = Unit

    /**
     * Configures the [Retrofit.Builder].
     *
     * @param builder the [Retrofit.Builder].
     */
    protected open fun configureRetrofit(builder: Retrofit.Builder) = Unit

    /**
     * Provides the response [TypeToken] for the custom deserializers.
     * @see com.edfapg.sdk.feature.deserializer
     *
     * @param Response the response type.
     */
    protected inline fun <reified Response> responseType() = object : TypeToken<Response>() {}.type

    /**
     * Enqueues the default Retrofit [Callback] by the inner custom [edfapayCallback].
     * Stands for providing the correct [Result] and [Response].
     * @see EdfaPgResponse
     * @see EdfaPgError
     *
     * @param Result the result type for the [Response].
     * @param Response the custom response type of the request.
     * @param edfapayCallback the custom [EdfaPgCallback].
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <Result, Response : EdfaPgResponse<Result>> Call<Response>.edfapayEnqueue(
        edfapayCallback: EdfaPgCallback<Result, Response>
    ) {
        return enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val body = response.body()
                val errorBody = response.errorBody()

                when {
                    body != null -> {
                        edfapayCallback.onResponse(body)
                        when (body) {
                            is EdfaPgResponse.Result<*> -> edfapayCallback.onResult(body.result as Result)
                            is EdfaPgResponse.Error<*> -> edfapayCallback.onError(body.error)
                            else -> onFailure(call, IllegalAccessException())
                        }
                    }

                    errorBody != null -> {
                        try {
                            val json = gson.toJsonTree(errorBody.charStream())
                            val error = gson.fromJson(errorBody.charStream(), EdfaPgError::class.java)
                            if(error == null){
                                onFailure(call, Exception(response.code().toString()))
                                return
                            }
                            edfapayCallback.onResponse(EdfaPgResponse.Error<Result>(error, json.asJsonObject) as Response)
                            edfapayCallback.onError(error)
                        } catch (e:Exception){
                            onFailure(call, Exception(response.code().toString()))
                        }
                    }

                    else -> {
                        onFailure(call, NullPointerException())
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                edfapayCallback.onFailure(t)
            }
        })
    }
}
