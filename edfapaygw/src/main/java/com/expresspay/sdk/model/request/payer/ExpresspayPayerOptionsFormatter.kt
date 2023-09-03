/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.request.payer

import java.text.SimpleDateFormat
import java.util.*

/**
 * The [EdfaPgPayerOptions] values formatter.
 */
internal class EdfaPgPayerOptionsFormatter {

    private val BIRTHDATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    /**
     * Validate and format [EdfaPgPayerOptions.birthdate] value.
     *
     * @param payerOptions the [EdfaPgPayerOptions].
     */
    fun birthdateFormat(payerOptions: EdfaPgPayerOptions?): String? = payerOptions?.birthdate?.let {
        BIRTHDATE_FORMAT.format(it)
    }
}
