/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.adapter

import android.net.Uri
import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.core.EdfaPgCredential
import com.edfapg.sdk.feature.deserializer.EdfaPgSadadDeserializer
import com.edfapg.sdk.feature.service.EdfaPgSadadService
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadCallback
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResponse
import com.edfapg.sdk.toolbox.EdfaPgHashUtil
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.edfapg.sdk.toolbox.EdfaPgValidation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.http.Field

/**
 * The API Adapter for the GET_TRANS_STATUS operation.
 * @see EdfaPgSadadService
 * @see EdfaPgSadadDeserializer
 * @see EdfaPgSadadCallback
 * @see EdfaPgSadadResponse
 */
object EdfaPgSadadAdapter : EdfaPgBaseAdapter<EdfaPgSadadService>() {

    override fun provideServiceClass(): Class<EdfaPgSadadService> =
        EdfaPgSadadService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<EdfaPgSadadResponse>(),
            EdfaPgSadadDeserializer()
        )
    }


    /**
     * Executes the [EdfaPgSadadService.generateSadadNumber] request.
     *
     * @param orderId unique order ID.
     * @param orderDescription  the description of Order.
     * @param orderAmount total order amount.
     * @param customerName the name of the customer sadad number created for.
     * @param mobileNumber the mobile number of the customer sadad number created for.
     * @param callback the [EdfaPgSadadCallback].
     */
    fun execute(
        @NonNull
        @Size(max = EdfaPgValidation.Text.REGULAR)
        orderId: String,
        @NonNull
        @Size(max = EdfaPgValidation.Text.LONG)
        orderDescription: String,
        @NonNull
        @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
        orderAmount: Double,
        @NonNull
        @Size(max = EdfaPgValidation.Text.LONG)
        customerName: String,
        @NonNull
        @Size(max = EdfaPgValidation.Text.SHORT)
        mobileNumber: String,
        @NonNull
        callback: EdfaPgSadadCallback
    ) {
        val body = mapOf(
            "orderId" to orderId,
            "orderDescription" to orderDescription,
            "orderAmount" to orderAmount,
            "customerName" to customerName,
            "mobileNumber" to mobileNumber
        )
        val json = Gson().toJson(body)
        val pass = EdfaPgCredential.clientPass()
        val hash = EdfaPgHashUtil.sha256("$json$pass")

        service.generateSadadNumber(
            url = paymentUrl(),
            merchantKey = EdfaPgCredential.clientKey(),
            hash = hash,
            body = body
        ).edfapayEnqueue(callback)
    }

    private fun paymentUrl() : String{
        val url = EdfaPgUtil.validateBaseUrl(EdfaPgCredential.paymentUrl())
        val uri = Uri.parse(url)
        val baseUrl = "${uri.scheme}://${uri.host}/sadad-service/public/s2s/one-time-invoice"
        return baseUrl
    }
}
