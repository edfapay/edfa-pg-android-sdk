/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.request.order

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import java.io.Serializable

/**
 * The required order data holder.
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgRecurringSaleAdapter
 * @see IEdfaPgOrder
 */
data class EdfaPgOrder(
    @NonNull
    @Size(max = EdfaPgValidation.Text.REGULAR)
    override val id: String,
    @NonNull
    @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
    override val amount: Double,
    @NonNull
    @Size(max = EdfaPgValidation.Text.LONG)
    override val description: String,
) : IEdfaPgOrder, Serializable
