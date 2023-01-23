/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.core.ExpresspayCredential
import com.expresspay.sdk.feature.deserializer.ExpresspaySaleDeserializer
import com.expresspay.sdk.feature.service.ExpresspaySaleService
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayOption
import com.expresspay.sdk.model.request.card.ExpresspayCard
import com.expresspay.sdk.model.request.card.ExpresspayCardFormatter
import com.expresspay.sdk.model.request.options.ExpresspaySaleOptions
import com.expresspay.sdk.model.request.order.ExpresspaySaleOrder
import com.expresspay.sdk.model.request.payer.ExpresspayPayer
import com.expresspay.sdk.model.request.payer.ExpresspayPayerOptionsFormatter
import com.expresspay.sdk.model.response.sale.ExpresspaySaleCallback
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.toolbox.ExpresspayAmountFormatter
import com.expresspay.sdk.toolbox.ExpresspayHashUtil
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.google.gson.GsonBuilder

/**
 * The API Adapter for the SALE operation.
 * @see ExpresspaySaleService
 * @see ExpresspaySaleDeserializer
 * @see ExpresspaySaleCallback
 * @see ExpresspaySaleResponse
 */
object ExpresspaySaleAdapter : ExpresspayBaseAdapter<ExpresspaySaleService>() {

    private val expresspayAmountFormatter = ExpresspayAmountFormatter()
    private val expresspayCardFormatter = ExpresspayCardFormatter()
    private val expresspayPayerOptionsFormatter = ExpresspayPayerOptionsFormatter()

    override fun provideServiceClass(): Class<ExpresspaySaleService> = ExpresspaySaleService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<ExpresspaySaleResponse>(),
            ExpresspaySaleDeserializer()
        )
    }

    /**
     * Executes the [ExpresspaySaleService.sale] request.
     *
     * @param order the [ExpresspaySaleOrder].
     * @param card the [ExpresspayCard].
     * @param payer the [ExpresspayPayer].
     * @param termUrl3ds URL to which Customer should be returned after 3D-Secure. String up to 1024 characters.
     * @param options the [ExpresspaySaleOptions]. Optional.
     * @param auth indicates that transaction must be only authenticated, but not captured.
     * @param callback the [ExpresspaySaleCallback].
     */
    fun execute(
        @NonNull
        order: ExpresspaySaleOrder,
        @NonNull
        card: ExpresspayCard,
        @NonNull
        payer: ExpresspayPayer,
        @NonNull
        @Size(max = ExpresspayValidation.Text.LONG)
        termUrl3ds: String,
        @Nullable
        options: ExpresspaySaleOptions? = null,
        @NonNull
        auth: Boolean,
        @NonNull
        callback: ExpresspaySaleCallback
    ) {
        val hash = ExpresspayHashUtil.hash(
            email = payer.email,
            cardNumber = card.number
        )
        val payerOptions = payer.options

        service.sale(
            url = ExpresspayCredential.paymentUrl(),
            action = ExpresspayAction.SALE.action,
            clientKey = ExpresspayCredential.clientKey(),
            orderId = order.id,
            orderAmount = expresspayAmountFormatter.amountFormat(order.amount),
            orderCurrency = order.currency,
            orderDescription = order.description,
            cardNumber = card.number,
            cardExpireMonth = expresspayCardFormatter.expireMonthFormat(card),
            cardExpireYear = expresspayCardFormatter.expireYearFormat(card),
            cardCvv2 = card.cvv,
            payerFirstName = payer.firstName,
            payerLastName = payer.lastName,
            payerAddress = payer.address,
            payerCountry = payer.country,
            payerCity = payer.city,
            payerZip = payer.zip,
            payerEmail = payer.email,
            payerPhone = payer.phone,
            payerIp = payer.ip,
            termUrl3ds = termUrl3ds,
            hash = hash,
            auth = ExpresspayOption.map(auth).option,
            channelId = if (options?.channelId.isNullOrEmpty()) null else options?.channelId,
            recurringInit = options?.recurringInit?.let { ExpresspayOption.map(it).option },
            payerMiddleName = if (payerOptions?.middleName.isNullOrEmpty()) null else payerOptions?.middleName,
            payerAddress2 = if (payerOptions?.address2.isNullOrEmpty()) null else payerOptions?.address2,
            payerState = if (payerOptions?.state.isNullOrEmpty()) null else payerOptions?.state,
            payerBirthDate = expresspayPayerOptionsFormatter.birthdateFormat(payerOptions)
        ).expresspayEnqueue(callback)
    }
}
