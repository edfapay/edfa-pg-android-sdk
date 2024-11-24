/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.payer

import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import java.io.Serializable
import java.util.*

/**
 * The optional payer options data holder.
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 * @see EdfaPgPayer
 *
 * @property middleName customer’s middle name. String up to 32 characters.
 * @property birthdate customer’s birthday. Format: yyyy-MM-dd, e.g. 1970-02-17.
 * @property address2 the adjoining road or locality of the сustomer’s address. String up to 255 characters.
 * @property state customer’s state. String up to 32 characters.
 */
data class EdfaPgPayerOptions(
    @Nullable
    @Size(max = EdfaPgValidation.Text.SHORT)
    val middleName: String? = "undefined",
    @Nullable
    val birthdate: Date? = null,
    @Nullable
    @Size(max = EdfaPgValidation.Text.REGULAR)
    val address2: String? = "undefined",
    @Nullable
    @Size(max = EdfaPgValidation.Text.SHORT)
    val state: String? = "undefined",
) : Serializable
