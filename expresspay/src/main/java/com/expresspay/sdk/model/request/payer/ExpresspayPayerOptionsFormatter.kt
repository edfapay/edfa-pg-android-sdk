/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.payer

import java.text.SimpleDateFormat
import java.util.*

/**
 * The [ExpresspayPayerOptions] values formatter.
 */
internal class ExpresspayPayerOptionsFormatter {

    private val BIRTHDATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    /**
     * Validate and format [ExpresspayPayerOptions.birthdate] value.
     *
     * @param payerOptions the [ExpresspayPayerOptions].
     */
    fun birthdateFormat(payerOptions: ExpresspayPayerOptions?): String? = payerOptions?.birthdate?.let {
        BIRTHDATE_FORMAT.format(it)
    }
}
