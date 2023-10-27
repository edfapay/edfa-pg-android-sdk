/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.options

import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import java.io.Serializable

/**
 * The optional sale options for the [com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgRecurringSaleAdapter
 *
 * @property channelId payment channel (Sub-account). String up to 16 characters.
 * @property recurringInit initialization of the transaction with possible following recurring.
 */
data class EdfaPgSaleOptions(
    @Nullable
    @Size(max = EdfaPgValidation.Text.CHANNEL_ID)
    val channelId: String? = null,
    @Nullable
    val recurringInit: Boolean? = null,
) : Serializable
