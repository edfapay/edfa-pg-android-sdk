/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.adapter

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.core.EdfaPgCredential
import com.edfapg.sdk.feature.deserializer.EdfaPgGetTransactionDetailsDeserializer
import com.edfapg.sdk.feature.service.EdfaPgGetTransactionDetailsService
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsCallback
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResponse
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResult
import com.edfapg.sdk.toolbox.EdfaPgHashUtil
import com.edfapg.sdk.toolbox.EdfaPgValidation
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the GET_TRANS_DETAILS operation.
 * @see EdfaPgGetTransactionDetailsService
 * @see EdfaPgGetTransactionDetailsDeserializer
 * @see EdfaPgGetTransactionDetailsCallback
 * @see EdfaPgGetTransactionDetailsResponse
 */
object EdfaPgGetTransactionDetailsAdapter : EdfaPgBaseAdapter<EdfaPgGetTransactionDetailsService>() {

    override fun provideServiceClass(): Class<EdfaPgGetTransactionDetailsService> =
        EdfaPgGetTransactionDetailsService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<EdfaPgGetTransactionDetailsResponse>(),
            EdfaPgGetTransactionDetailsDeserializer()
        )
    }

    /**
     * Executes the [EdfaPgGetTransactionDetailsService.getTransactionDetails] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param callback the [EdfaPgGetTransactionDetailsCallback].
     */
    fun execute(
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        transactionId: String,
        @NonNull
        @Size(max = EdfaPgValidation.Text.REGULAR)
        payerEmail: String,
        @NonNull
        @Size(min = EdfaPgValidation.Card.CARD_NUMBER_MIN, max = EdfaPgValidation.Card.CARD_NUMBER_MAX)
        cardNumber: String,
        @NonNull
        callback: EdfaPgGetTransactionDetailsCallback
    ) {
        val hash = EdfaPgHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, callback)
    }

    /**
     * Executes the [EdfaPgGetTransactionDetailsService.getTransactionDetails] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param callback the [EdfaPgGetTransactionDetailsCallback].
     */
    private const val MAX_RETRY = 3
    private const val RETRY_DELAY = 500L

    fun execute(
        @NonNull
        transactionId: String,
        @NonNull
        hash: String,
        @NonNull
        callback: EdfaPgGetTransactionDetailsCallback
    ) {
        executeWithRetry(transactionId, hash, callback, retriesLeft = MAX_RETRY)
    }

    private fun executeWithRetry(
        transactionId: String,
        hash: String,
        callback: EdfaPgGetTransactionDetailsCallback,
        retriesLeft: Int
    ) {
        service.getTransactionDetails(
            url = EdfaPgCredential.paymentUrl(),
            action = EdfaPgAction.GET_TRANS_DETAILS.action,
            clientKey = EdfaPgCredential.clientKey(),
            transactionId = transactionId,
            hash = hash
        ).edfapayEnqueue(object : EdfaPgGetTransactionDetailsCallback {
            override fun onResult(result: EdfaPgGetTransactionDetailsResult) {
                callback.onResult(result)
            }

            override fun onError(error: EdfaPgError) {
                if (error.code == 100000 && retriesLeft > 0) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        executeWithRetry(transactionId, hash, callback, retriesLeft - 1)
                    }, RETRY_DELAY)
                } else {
                    callback.onError(error)
                }
            }
        })
    }
}
