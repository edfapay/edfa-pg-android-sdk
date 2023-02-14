/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspayCreditvoidDeserializer
import com.expresspay.sdk.feature.service.ExpresspayCreditvoidService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.creditvoid.ExpresspayCreditvoidCallback
import com.expresspay.sdk.model.response.creditvoid.ExpresspayCreditvoidResponse
import com.expresspay.sdk.toolbox.ExpresspayAmountFormatter
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the CREDITVOID operation.
 * @see ExpresspayCreditvoidService
 * @see ExpresspayCreditvoidDeserializer
 * @see ExpresspayCreditvoidCallback
 * @see ExpresspayCreditvoidResponse
 */
object ExpresspayCreditvoidAdapter : ExpresspayBaseAdapter<ExpresspayCreditvoidService>() {

    private val expresspayAmountFormatter = ExpresspayAmountFormatter()

    override fun provideServiceClass(): Class<ExpresspayCreditvoidService> = ExpresspayCreditvoidService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspayCreditvoidResponse>(),
            ExpresspayCreditvoidDeserializer()
        )
    }

    /**
     * Executes the [ExpresspayCreditvoidService.creditvoid] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [ExpresspayCreditvoidCallback].
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
        callback: ExpresspayCreditvoidCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, amount, callback)
    }

    /**
     * Executes the [ExpresspayCreditvoidService.creditvoid] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [ExpresspayCreditvoidCallback].
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
        callback: ExpresspayCreditvoidCallback
    ) {
        service.creditvoid(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.CREDITVOID.action,
            clientKey = ExpresspayCredential.clientKey(),
            transactionId = transactionId,
            amount = amount?.let { expresspayAmountFormatter.amountFormat(it) },
            hash = hash
        ).expresspayEnqueue(callback)
    }
}
