/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspaySaleDeserializer
import com.expresspay.sdk.feature.service.ExpresspayRecurringSaleService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayOption
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.request.options.ExpresspayRecurringOptions
import com.expresspay.sdk.model.request.order.ExpresspayOrder
import com.expresspay.sdk.model.response.sale.ExpresspaySaleCallback
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.toolbox.ExpresspayAmountFormatter
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the RECURRING_SALE operation.
 * @see ExpresspayRecurringSaleService
 * @see ExpresspaySaleDeserializer
 * @see ExpresspaySaleCallback
 * @see ExpresspaySaleResponse
 */
object ExpresspayRecurringSaleAdapter : ExpresspayBaseAdapter<ExpresspayRecurringSaleService>() {

    private val expresspayAmountFormatter = ExpresspayAmountFormatter()

    override fun provideServiceClass(): Class<ExpresspayRecurringSaleService> =
        ExpresspayRecurringSaleService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspaySaleResponse>(),
            ExpresspaySaleDeserializer()
        )
    }

    /**
     * Executes the [ExpresspayRecurringSaleService.recurringSale] request.
     *
     * @param order the [ExpresspayOrder].
     * @param options the [ExpresspayRecurringOptions].
     * @param payerEmail customer’s email. String up to 256 characters.
     * @param cardNumber the credit card number.
     * @param auth indicates that transaction must be only authenticated, but not captured.
     * @param callback the [ExpresspaySaleCallback].
     */
    fun execute(
        @NonNull
        order: ExpresspayOrder,
        @NonNull
        options: ExpresspayRecurringOptions,
        @NonNull
        @Size(max = ExpresspayValidation.Text.REGULAR)
        payerEmail: String,
        @NonNull
        @Size(min = ExpresspayValidation.Card.CARD_NUMBER_MIN, max = ExpresspayValidation.Card.CARD_NUMBER_MAX)
        cardNumber: String,
        @NonNull
        auth: Boolean,
        @NonNull
        callback: ExpresspaySaleCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payerEmail,
            cardNumber = cardNumber
        )

        execute(order, options, hash, auth, callback)
    }

    /**
     * Executes the [ExpresspayRecurringSaleService.recurringSale] request.
     *
     * @param order the [ExpresspayOrder].
     * @param options the [ExpresspayRecurringOptions].
     * @param hash special signature to validate your request to payment platform.
     * @param auth indicates that transaction must be only authenticated, but not captured.
     * @param callback the [ExpresspaySaleCallback].
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     */
    fun execute(
        @NonNull
        order: ExpresspayOrder,
        @NonNull
        options: ExpresspayRecurringOptions,
        @NonNull
        hash: String,
        @NonNull
        auth: Boolean,
        @NonNull
        callback: ExpresspaySaleCallback
    ) {
        service.recurringSale(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.RECURRING_SALE.action,
            clientKey = ExpresspayCredential.clientKey(),
            orderId = order.id,
            orderAmount = expresspayAmountFormatter.amountFormat(order.amount),
            orderDescription = order.description,
            recurringFirstTransactionId = options.firstTransactionId,
            recurringToken = options.token,
            auth = ExpresspayOption.map(auth).option,
            hash = hash
        ).expresspayEnqueue(callback)
    }
}
