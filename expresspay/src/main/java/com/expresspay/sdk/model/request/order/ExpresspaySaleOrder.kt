/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.order

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import java.io.Serializable

/**
 * The sale order data holder.
 * @see com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter
 * @see IExpresspayOrder
 *
 * @property currency the currency. 3-letter code.
 */
data class ExpresspaySaleOrder(
    @NonNull
    @Size(max = ExpresspayValidation.Text.REGULAR)
    override val id: String,
    @NonNull
    @FloatRange(from = ExpresspayValidation.Amount.VALUE_MIN)
    override val amount: Double,
    @NonNull
    @Size(max = ExpresspayValidation.Text.CURRENCY)
    val currency: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.LONG)
    override val description: String,
) : IExpresspayOrder, Serializable
