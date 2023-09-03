/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapaygw.sdk.core.EdfaPgCredential
import com.edfapaygw.sdk.feature.deserializer.EdfaPgGetTransactionDetailsDeserializer
import com.edfapaygw.sdk.feature.service.EdfaPgGetTransactionDetailsService
import com.edfapaygw.sdk.model.api.EdfaPgAction
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import com.edfapaygw.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsCallback
import com.edfapaygw.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResponse
import com.edfapaygw.sdk.toolbox.EdfaPgHashUtil
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
    fun execute(
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        transactionId: String,
        @NonNull
        hash: String,
        @NonNull
        callback: EdfaPgGetTransactionDetailsCallback
    ) {
        service.getTransactionDetails(
            url = EdfaPgCredential.paymentUrl(),
            action = EdfaPgAction.GET_TRANS_DETAILS.action,
            clientKey = EdfaPgCredential.clientKey(),
            transactionId = transactionId,
            hash = hash
        ).edfapayEnqueue(callback)
    }
}
