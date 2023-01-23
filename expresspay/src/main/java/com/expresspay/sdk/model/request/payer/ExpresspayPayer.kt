/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.payer

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import java.io.Serializable

/**
 * The required payer data holder.
 * @see com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter
 * @see ExpresspayPayerOptions
 *
 * @property firstName customer’s name. String up to 32 characters.
 * @property lastName customer’s surname. String up to 32 characters.
 * @property address customer’s address. String up to 255 characters.
 * @property country customer’s country. 2-letter code.
 * @property city customer’s city. String up to 32 characters.
 * @property zip ZIP-code of the Customer. String up to 32 characters.
 * @property email customer’s email. String up to 256 characters.
 * @property phone customer’s phone. String up to 32 characters.
 * @property ip IP-address of the Customer. XXX.XXX.XXX.XXX.
 * @property options the optional [ExpresspayPayerOptions].
 */
data class ExpresspayPayer(
    @NonNull
    @Size(max = ExpresspayValidation.Text.SHORT)
    val firstName: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.SHORT)
    val lastName: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.REGULAR)
    val address: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.COUNTRY)
    val country: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.SHORT)
    val city: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.SHORT)
    val zip: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.REGULAR)
    val email: String,
    @NonNull
    @Size(max = ExpresspayValidation.Text.SHORT)
    val phone: String,
    @NonNull
    @Size(min = ExpresspayValidation.Text.IP_MIN, max = ExpresspayValidation.Text.IP_MAX)
    val ip: String,
    @Nullable
    val options: ExpresspayPayerOptions? = null,
) : Serializable
