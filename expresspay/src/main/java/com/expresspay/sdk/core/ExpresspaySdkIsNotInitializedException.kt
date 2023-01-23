/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.core

/**
 * The [ExpresspaySdk] not initialized exception. Thrown when [ExpresspayCredential.requireInit] is not satisfied with
 * the [ExpresspayCredential.isInitialized] soft check.
 */
class ExpresspaySdkIsNotInitializedException : IllegalAccessError(MESSAGE) {

    companion object {
        private const val MESSAGE = """
           Expresspay SDK is not initialized.
           Please, provide your Payment Platform credentials in AndroidManifest.xml,
           and call ExpresspaySdk.init(yourAppContext) in YourApplication.class.
           You can also initialize the Expresspay SDK programmatically.
        """
    }
}
