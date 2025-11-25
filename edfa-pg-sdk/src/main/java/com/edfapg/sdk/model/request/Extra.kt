/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request

import androidx.annotation.NonNull
import com.edfapg.sdk.model.request.order.IEdfaPgOrder
import java.io.Serializable

/**
 * The required order data holder.
 * @see com.edfapg.sdk.feature.adapter.EdfaPgRecurringSaleAdapter
 * @see IEdfaPgOrder
 */
data class Extra(
    @NonNull
    val type: String,
    @NonNull
    val name: String,
    @NonNull
    val value:String
) : Serializable
