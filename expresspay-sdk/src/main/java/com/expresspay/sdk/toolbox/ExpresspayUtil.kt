/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.toolbox

import java.io.File

/**
 * The general [com.expresspay.sdk.core.ExpresspaySdk] utils.
 */
internal object ExpresspayUtil {
    val ProcessCompleteCallbackUrl = "https://expresspay.sa/process-completed"

    /**
     * Validate and return the base url (Payment URL).
     * @see com.expresspay.sdk.core.ExpresspayCredential.PAYMENT_URL
     *
     * @param baseUrl the base url.
     * @return the validated base url String.
     */
    fun validateBaseUrl(baseUrl: String): String {
        return if (baseUrl.endsWith(File.separatorChar)) {
            baseUrl
        } else {
            baseUrl.plus(File.separatorChar)
        }
    }
}
