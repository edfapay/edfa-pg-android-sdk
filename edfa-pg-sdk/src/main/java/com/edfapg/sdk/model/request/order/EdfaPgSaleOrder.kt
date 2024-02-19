/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.order

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import java.io.Serializable
import java.util.Currency

/**
 * The sale order data holder.
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 * @see IEdfaPgOrder
 *
 * @property currency the currency. 3-letter code.
 */
data class EdfaPgSaleOrder(
    @NonNull
    @Size(max = EdfaPgValidation.Text.REGULAR)
    override val id: String,
    @NonNull
    @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
    override val amount: Double,
    @NonNull
    @Size(max = EdfaPgValidation.Text.CURRENCY)
    val currency: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.LONG)
    override val description: String,
) : IEdfaPgOrder, Serializable {

    fun formattedAmount(): String {
        val fractionDigits = Currency.getInstance(currency).defaultFractionDigits
        return String.format("%.${fractionDigits}f", amount)
    }

    fun formattedCurrency(): String {
        val _currency = Currency.getInstance(currency)
        return _currency.currencyCode
    }
}
