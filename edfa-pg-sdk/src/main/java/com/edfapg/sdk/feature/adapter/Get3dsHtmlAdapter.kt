package com.edfapg.sdk.feature.adapter

import com.edfapg.sdk.BuildConfig
import com.edfapg.sdk.core.ENABLE_DEBUG
import com.edfapg.sdk.model.response.sale.EdfaPgSaleRedirect
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

object Get3dsHtmlAdapter {

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG || ENABLE_DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logging)
        }

        builder.build()
    }

    fun execute(redirectParams: EdfaPgSaleRedirect, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val json = JSONObject().apply {
            put("body", redirectParams.redirectParams.body)
        }.toString()

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(redirectParams.redirectUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val body = response.body?.string()
                if (response.isSuccessful && body != null) {
                    onSuccess(body)
                } else {
                    onError(body ?: "Failed to collect verification data")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onError(e.localizedMessage ?: e.message ?: "Unknown error")
            }
        })
    }
}