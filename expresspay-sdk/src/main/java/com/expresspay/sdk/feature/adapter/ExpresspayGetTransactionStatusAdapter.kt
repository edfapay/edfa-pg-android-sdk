/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspayGetTransactionStatusDeserializer
import com.expresspay.sdk.feature.service.ExpresspayGetTransactionStatusService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.gettransactionstatus.ExpresspayGetTransactionStatusCallback
import com.expresspay.sdk.model.response.gettransactionstatus.ExpresspayGetTransactionStatusResponse
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the GET_TRANS_STATUS operation.
 * @see ExpresspayGetTransactionStatusService
 * @see ExpresspayGetTransactionStatusDeserializer
 * @see ExpresspayGetTransactionStatusCallback
 * @see ExpresspayGetTransactionStatusResponse
 */
object ExpresspayGetTransactionStatusAdapter : ExpresspayBaseAdapter<ExpresspayGetTransactionStatusService>() {

    override fun provideServiceClass(): Class<ExpresspayGetTransactionStatusService> =
        ExpresspayGetTransactionStatusService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspayGetTransactionStatusResponse>(),
            ExpresspayGetTransactionStatusDeserializer()
        )
    }

    /**
     * Executes the [ExpresspayGetTransactionStatusService.getTransactionStatus] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param callback the [ExpresspayGetTransactionStatusCallback].
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
        callback: ExpresspayGetTransactionStatusCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, callback)
    }

    /**
     * Executes the [ExpresspayGetTransactionStatusService.getTransactionStatus] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param callback the [ExpresspayGetTransactionStatusCallback].
     */
    fun execute(
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        transactionId: String,
        @NonNull
        hash: String,
        @NonNull
        callback: ExpresspayGetTransactionStatusCallback
    ) {
        service.getTransactionStatus(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.GET_TRANS_STATUS.action,
            clientKey = ExpresspayCredential.clientKey(),
            transactionId = transactionId,
            hash = hash
        ).expresspayEnqueue(callback)
    }
}
