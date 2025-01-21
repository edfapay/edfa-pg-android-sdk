/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.adapter

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapg.sdk.core.EdfaPgCredential
import com.edfapg.sdk.feature.deserializer.EdfaPgSaleDeserializer
import com.edfapg.sdk.feature.service.EdfaPgSaleService
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgOption
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.card.EdfaPgCardFormatter
import com.edfapg.sdk.model.request.options.EdfaPgSaleOptions
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.request.payer.EdfaPgPayerOptionsFormatter
import com.edfapg.sdk.model.response.sale.EdfaPgSaleCallback
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.EdfaPgAmountFormatter
import com.edfapg.sdk.toolbox.EdfaPgHashUtil
import com.edfapg.sdk.toolbox.EdfaPgValidation
import com.google.gson.GsonBuilder
import java.util.*

/**
 * The API Adapter for the SALE operation.
 * @see EdfaPgSaleService
 * @see EdfaPgSaleDeserializer
 * @see EdfaPgSaleCallback
 * @see EdfaPgSaleResponse
 */
object EdfaPgSaleAdapter : EdfaPgBaseAdapter<EdfaPgSaleService>() {

    private val edfapayAmountFormatter = EdfaPgAmountFormatter()
    private val edfapayCardFormatter = EdfaPgCardFormatter()
    private val edfapayPayerOptionsFormatter = EdfaPgPayerOptionsFormatter()

    override fun provideServiceClass(): Class<EdfaPgSaleService> = EdfaPgSaleService::class.java

    override fun configureGson(builder: GsonBuilder) {
        super.configureGson(builder)
        builder.registerTypeAdapter(
            responseType<EdfaPgSaleResponse>(),
            EdfaPgSaleDeserializer()
        )
    }

    /**
     * Executes the [EdfaPgSaleService.sale] request.
     *
     * @param order the [EdfaPgSaleOrder].
     * @param card the [EdfaPgCard].
     * @param payer the [EdfaPgPayer].
     * @param termUrl3ds URL to which Customer should be returned after 3D-Secure. String up to 1024 characters.
     * @param options the [EdfaPgSaleOptions]. Optional.
     * @param auth indicates that transaction must be only authenticated, but not captured.
     * @param callback the [EdfaPgSaleCallback].
     */
    fun execute(
        @NonNull
        order: EdfaPgSaleOrder,
        @NonNull
        card: EdfaPgCard,
        @NonNull
        payer: EdfaPgPayer,
        @NonNull
        @Size(max = EdfaPgValidation.Text.LONG)
        termUrl3ds: String,
        @Nullable
        options: EdfaPgSaleOptions? = null,
        @NonNull
        auth: Boolean,
        reqToken: Boolean = false,
        @NonNull
        callback: EdfaPgSaleCallback
    ) {
        val hash = EdfaPgHashUtil.hash(
            email = payer.email,
            cardNumber = card.number
        )
        val payerOptions = payer.options

        service.sale(
            url = EdfaPgCredential.paymentUrl(),
            action = EdfaPgAction.SALE.action,
            clientKey = EdfaPgCredential.clientKey(),
            orderId = order.id,
            orderAmount = edfapayAmountFormatter.amountFormat(order.amount),
            orderCurrency = String.format(Locale.US,"%s", order.currency),
            orderDescription = order.description,
            cardNumber = String.format(Locale.US,"%s", card.number),
            cardExpireMonth = edfapayCardFormatter.expireMonthFormat(card),
            cardExpireYear = edfapayCardFormatter.expireYearFormat(card),
            cardCvv2 = String.format(Locale.US,"%s", card.cvv),
            payerFirstName = payer.firstName,
            payerLastName = payer.lastName,
            payerAddress = payer.address,
            payerCountry = payer.country,
            payerCity = payer.city,
            payerZip = String.format(Locale.US,"%s", payer.zip),
            payerEmail = payer.email,
            payerPhone = String.format(Locale.US,"%s", payer.phone),
            payerIp = String.format(Locale.US,"%s", payer.ip),
            termUrl3ds = termUrl3ds,
            hash = hash,
            auth = EdfaPgOption.map(auth).option,
            channelId = if (options?.channelId.isNullOrEmpty()) null else options?.channelId,
            recurringInit = options?.recurringInit?.let { EdfaPgOption.map(it).option },
            payerMiddleName = if (payerOptions?.middleName.isNullOrEmpty()) null else payerOptions?.middleName,
            payerAddress2 = if (payerOptions?.address2.isNullOrEmpty()) null else payerOptions?.address2,
            payerState = if (payerOptions?.state.isNullOrEmpty()) null else payerOptions?.state,
            payerBirthDate = edfapayPayerOptionsFormatter.birthdateFormat(payerOptions),
            reqToken = if(reqToken) "Y" else "N"
        ).edfapayEnqueue(callback)
    }
}
