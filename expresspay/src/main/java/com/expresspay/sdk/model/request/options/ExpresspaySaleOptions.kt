/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.options

import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import java.io.Serializable

/**
 * The optional sale options for the [com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayRecurringSaleAdapter
 *
 * @property channelId payment channel (Sub-account). String up to 16 characters.
 * @property recurringInit initialization of the transaction with possible following recurring.
 */
data class ExpresspaySaleOptions(
    @Nullable
    @Size(max = ExpresspayValidation.Text.CHANNEL_ID)
    val channelId: String? = null,
    @Nullable
    val recurringInit: Boolean? = null,
) : Serializable
