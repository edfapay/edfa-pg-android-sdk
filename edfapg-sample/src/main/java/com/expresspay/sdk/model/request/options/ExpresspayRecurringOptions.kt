/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.options

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import java.io.Serializable

/**
 * The required recurring options for the [com.edfapg.sdk.feature.adapter.EdfaPgRecurringSaleAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 *
 * @property firstTransactionId transaction ID of the primary transaction in the Payment Platform. UUID format value.
 * @property token value obtained during the primary transaction. UUID format value.
 */
data class EdfaPgRecurringOptions(
    @NonNull
    @Size(EdfaPgValidation.Text.UUID)
    val firstTransactionId: String,
    @NonNull
    @Size(EdfaPgValidation.Text.UUID)
    val token: String,
) : Serializable
