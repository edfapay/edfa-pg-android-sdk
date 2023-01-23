/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspayGetTransactionDetailsDeserializer
import com.expresspay.sdk.feature.service.ExpresspayGetTransactionDetailsService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsCallback
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResponse
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the GET_TRANS_DETAILS operation.
 * @see ExpresspayGetTransactionDetailsService
 * @see ExpresspayGetTransactionDetailsDeserializer
 * @see ExpresspayGetTransactionDetailsCallback
 * @see ExpresspayGetTransactionDetailsResponse
 */
object ExpresspayGetTransactionDetailsAdapter : ExpresspayBaseAdapter<ExpresspayGetTransactionDetailsService>() {

    override fun provideServiceClass(): Class<ExpresspayGetTransactionDetailsService> =
        ExpresspayGetTransactionDetailsService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspayGetTransactionDetailsResponse>(),
            ExpresspayGetTransactionDetailsDeserializer()
        )
    }

    /**
     * Executes the [ExpresspayGetTransactionDetailsService.getTransactionDetails] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param callback the [ExpresspayGetTransactionDetailsCallback].
     */
    fun execute(
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        transactionId: String,
        @NonNull
        @Size(max = ExpresspayValidation.Text.REGULAR)
        payerEmail: String,
        @NonNull
        @Size(min = ExpresspayValidation.Card.CARD_NUMBER_MIN, max = ExpresspayValidation.Card.CARD_NUMBER_MAX)
        cardNumber: String,
        @NonNull
        callback: ExpresspayGetTransactionDetailsCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, callback)
    }

    /**
     * Executes the [ExpresspayGetTransactionDetailsService.getTransactionDetails] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param callback the [ExpresspayGetTransactionDetailsCallback].
     */
    fun execute(
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        transactionId: String,
        @NonNull
        hash: String,
        @NonNull
        callback: ExpresspayGetTransactionDetailsCallback
    ) {
        service.getTransactionDetails(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.GET_TRANS_DETAILS.action,
            clientKey = ExpresspayCredential.clientKey(),
            transactionId = transactionId,
            hash = hash
        ).expresspayEnqueue(callback)
    }
}
