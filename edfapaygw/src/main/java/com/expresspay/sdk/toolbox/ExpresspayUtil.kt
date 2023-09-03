/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.toolbox

import java.io.File

/**
 * The general [com.edfapaygw.sdk.core.EdfaPgSdk] utils.
 */
internal object EdfaPgUtil {

    /**
     * Validate and return the base url (Payment URL).
     * @see com.edfapaygw.sdk.core.EdfaPgCredential.PAYMENT_URL
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
