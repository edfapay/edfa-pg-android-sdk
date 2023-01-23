/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.options

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import java.io.Serializable

/**
 * The required recurring options for the [com.expresspay.sdk.feature.adapter.ExpresspayRecurringSaleAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter
 *
 * @property firstTransactionId transaction ID of the primary transaction in the Payment Platform. UUID format value.
 * @property token value obtained during the primary transaction. UUID format value.
 */
data class ExpresspayRecurringOptions(
    @NonNull
    @Size(ExpresspayValidation.Text.UUID)
    val firstTransactionId: String,
    @NonNull
    @Size(ExpresspayValidation.Text.UUID)
    val token: String,
) : Serializable
