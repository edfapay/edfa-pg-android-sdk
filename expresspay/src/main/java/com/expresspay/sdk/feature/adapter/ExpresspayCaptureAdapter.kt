/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspayCaptureDeserializer
import com.expresspay.sdk.feature.service.ExpresspayCaptureService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.response.capture.ExpresspayCaptureCallback
import com.expresspay.sdk.model.response.capture.ExpresspayCaptureResponse
import com.expresspay.sdk.toolbox.ExpresspayAmountFormatter
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the CAPTURE operation.
 * @see ExpresspayCaptureService
 * @see ExpresspayCaptureDeserializer
 * @see ExpresspayCaptureCallback
 * @see ExpresspayCaptureResponse
 */
object ExpresspayCaptureAdapter : ExpresspayBaseAdapter<ExpresspayCaptureService>() {

    private val expresspayAmountFormatter = ExpresspayAmountFormatter()

    override fun provideServiceClass(): Class<ExpresspayCaptureService> = ExpresspayCaptureService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspayCaptureResponse>(),
            ExpresspayCaptureDeserializer()
        )
    }

    /**
     * Executes the [ExpresspayCaptureService.capture] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [ExpresspayCaptureCallback].
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
        @Nullable
        @FloatRange(from = ExpresspayValidation.Amount.VALUE_MIN)
        amount: Double?,
        @NonNull
        callback: ExpresspayCaptureCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, amount, callback)
    }

    /**
     * Executes the [ExpresspayCaptureService.capture] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [ExpresspayCaptureCallback].
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     */
    fun execute(
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        transactionId: String,
        @NonNull
        hash: String,
        @Nullable
        @FloatRange(from = ExpresspayValidation.Amount.VALUE_MIN)
        amount: Double?,
        @NonNull
        callback: ExpresspayCaptureCallback
    ) {
        service.capture(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.CAPTURE.action,
            clientKey = ExpresspayCredential.clientKey(),
            transactionId = transactionId,
            amount = amount?.let { expresspayAmountFormatter.amountFormat(it) },
            hash = hash
        ).expresspayEnqueue(callback)
    }
}
