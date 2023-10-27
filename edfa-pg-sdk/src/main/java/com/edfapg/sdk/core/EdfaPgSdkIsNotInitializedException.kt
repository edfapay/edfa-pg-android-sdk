/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.core

/**
 * The [EdfaPgSdk] not initialized exception. Thrown when [EdfaPgCredential.requireInit] is not satisfied with
 * the [EdfaPgCredential.isInitialized] soft check.
 */
class EdfaPgSdkIsNotInitializedException : IllegalAccessError(MESSAGE) {

    companion object {
        private const val MESSAGE = """
           EdfaPg SDK is not initialized.
           Please, provide your Payment Platform credentials in AndroidManifest.xml,
           and call EdfaPgSdk.init(yourAppContext) in YourApplication.class.
           You can also initialize the EdfaPg SDK programmatically.
        """
    }
}
