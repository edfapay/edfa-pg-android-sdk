/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.payer

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import java.io.Serializable

/**
 * The required payer data holder.
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 * @see EdfaPgPayerOptions
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
 * @property options the optional [EdfaPgPayerOptions].
 */
data class EdfaPgPayer(
    @NonNull
    @Size(max = EdfaPgValidation.Text.SHORT)
    val firstName: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.SHORT)
    val lastName: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.REGULAR)
    val address: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.COUNTRY)
    val country: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.SHORT)
    val city: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.SHORT)
    val zip: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.REGULAR)
    val email: String,
    @NonNull
    @Size(max = EdfaPgValidation.Text.SHORT)
    val phone: String,
    @NonNull
    @Size(min = EdfaPgValidation.Text.IP_MIN, max = EdfaPgValidation.Text.IP_MAX)
    val ip: String,
    @Nullable
    val options: EdfaPgPayerOptions? = null
) : Serializable {


    fun validate(): List<String> {
        val errors = mutableListOf<String>()

        if (firstName.isBlank()) errors.add("firstName is empty")
        if (lastName.isBlank()) errors.add("lastName is empty")
        if (address.isBlank()) errors.add("address is empty")
        if (country.isBlank()) errors.add("country is empty")
        if (city.isBlank()) errors.add("city is empty")
        if (zip.isBlank()) errors.add("zip is empty")
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add("email is invalid")
        }
        if (phone.isBlank()) errors.add("phone is empty")
        if (!Regex("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").matches(ip)) {
            errors.add("IP address is invalid")
        }

        return errors
    }
}
