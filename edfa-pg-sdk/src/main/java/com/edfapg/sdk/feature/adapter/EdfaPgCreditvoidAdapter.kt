/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.adapter

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapg.sdk.core.EdfaPgCredential
import com.edfapg.sdk.feature.deserializer.EdfaPgCreditvoidDeserializer
import com.edfapg.sdk.feature.service.EdfaPgCreditvoidService
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.toolbox.EdfaPgValidation
import com.edfapg.sdk.model.response.creditvoid.EdfaPgCreditvoidCallback
import com.edfapg.sdk.model.response.creditvoid.EdfaPgCreditvoidResponse
import com.edfapg.sdk.toolbox.EdfaPgAmountFormatter
import com.edfapg.sdk.toolbox.EdfaPgHashUtil
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the CREDITVOID operation.
 * @see EdfaPgCreditvoidService
 * @see EdfaPgCreditvoidDeserializer
 * @see EdfaPgCreditvoidCallback
 * @see EdfaPgCreditvoidResponse
 */
object EdfaPgCreditvoidAdapter : EdfaPgBaseAdapter<EdfaPgCreditvoidService>() {

    private val edfapayAmountFormatter = EdfaPgAmountFormatter()

    override fun provideServiceClass(): Class<EdfaPgCreditvoidService> = EdfaPgCreditvoidService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<EdfaPgCreditvoidResponse>(),
            EdfaPgCreditvoidDeserializer()
        )
    }

    /**
     * Executes the [EdfaPgCreditvoidService.creditvoid] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [EdfaPgCreditvoidCallback].
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
        @Nullable
        @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
        amount: Double?,
        @NonNull
        callback: EdfaPgCreditvoidCallback
    ) {
        val hash = EdfaPgHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber,
            transactionId = transactionId
        )

        execute(transactionId, hash, amount, callback)
    }

    /**
     * Executes the [EdfaPgCreditvoidService.creditvoid] request.
     *
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to payment platform.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param callback the [EdfaPgCreditvoidCallback].
     * @see com.edfapg.sdk.toolbox.EdfaPgHashUtil
     */
    fun execute(
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        transactionId: String,
        @NonNull
        hash: String,
        @Nullable
        @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
        amount: Double?,
        @NonNull
        callback: EdfaPgCreditvoidCallback
    ) {
        service.creditvoid(
            url = EdfaPgCredential.paymentUrl(),
            action = EdfaPgAction.CREDITVOID.action,
            clientKey = EdfaPgCredential.clientKey(),
            transactionId = transactionId,
            amount = amount?.let { edfapayAmountFormatter.amountFormat(it) },
            hash = hash
        ).edfapayEnqueue(callback)
    }
}
