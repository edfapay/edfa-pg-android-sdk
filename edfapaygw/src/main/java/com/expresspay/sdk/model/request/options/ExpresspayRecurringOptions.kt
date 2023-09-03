/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.request.options

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import java.io.Serializable

/**
 * The required recurring options for the [com.edfapaygw.sdk.feature.adapter.EdfaPgRecurringSaleAdapter].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgSaleAdapter
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
