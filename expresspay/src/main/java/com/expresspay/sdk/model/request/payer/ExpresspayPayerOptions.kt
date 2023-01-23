/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.payer

import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import java.io.Serializable
import java.util.*

/**
 * The optional payer options data holder.
 * @see com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter
 * @see ExpresspayPayer
 *
 * @property middleName customer’s middle name. String up to 32 characters.
 * @property birthdate customer’s birthday. Format: yyyy-MM-dd, e.g. 1970-02-17.
 * @property address2 the adjoining road or locality of the сustomer’s address. String up to 255 characters.
 * @property state customer’s state. String up to 32 characters.
 */
data class ExpresspayPayerOptions(
    @Nullable
    @Size(max = ExpresspayValidation.Text.SHORT)
    val middleName: String? = null,
    @Nullable
    val birthdate: Date? = null,
    @Nullable
    @Size(max = ExpresspayValidation.Text.REGULAR)
    val address2: String? = null,
    @Nullable
    @Size(max = ExpresspayValidation.Text.SHORT)
    val state: String? = null,
) : Serializable
