/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.toolbox

import java.io.File

/**
 * The general [com.edfapg.sdk.core.EdfaPgSdk] utils.
 */
object EdfaPgUtil {
    var ProcessCompleteCallbackUrl = "https://edfapay.com/process-completed"

    /**
     * Validate and return the base url (Payment URL).
     * @see com.edfapg.sdk.core.EdfaPgCredential.PAYMENT_URL
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
