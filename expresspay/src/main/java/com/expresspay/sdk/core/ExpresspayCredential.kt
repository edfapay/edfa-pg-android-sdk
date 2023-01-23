/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.core

import android.content.Context
import android.os.Bundle
import com.expresspay.sdk.core.ExpresspayCredential.init

/**
 * The [ExpresspaySdk] credentials holder.
 * It stores and retrieve it automatically after the first successful [init].
 * [ExpresspayCredential] stores values in the [ExpresspayStorage].
 * @throws ExpresspaySdkIsNotInitializedException if [ExpresspaySdk] is not initialized.
 */
internal object ExpresspayCredential {

    /**
     * Unique key to identify the account in Payment Platform (used as request parameter).
     */
    private const val CLIENT_KEY = "com.expresspay.sdk.CLIENT_KEY"

    /**
     * Password for Client authentication in Payment Platform (used for calculating hash parameter).
     */
    private const val CLIENT_PASS = "com.expresspay.sdk.CLIENT_PASS"

    /**
     * URL to request the Payment Platform.
     */
    private const val PAYMENT_URL = "com.expresspay.sdk.PAYMENT_URL"

    private var _clientKey: String? = null
    private var _clientPass: String? = null
    private var _paymentUrl: String? = null

    private var _isInitialized: Boolean? = null
    private var expresspayStorage: ExpresspayStorage? = null

    /**
     * Initialize the [ExpresspayCredential] values.
     *
     * @param context the app context.
     * @param metadata the meta-data of the Application AndroidManifest.xml.
     */
    fun init(
        context: Context,
        metadata: Bundle
    ) {
        init(
            context,
            metadata.getString(CLIENT_KEY) ?: return,
            metadata.getString(CLIENT_PASS) ?: return,
            metadata.getString(PAYMENT_URL) ?: return
        )
    }

    /**
     * Initialize the [ExpresspayCredential] values.
     *
     * @param context the app context.
     * @param clientKey the [ExpresspayCredential.CLIENT_KEY] value.
     * @param clientPass the [ExpresspayCredential.CLIENT_PASS] value.
     * @param paymentUrl the [ExpresspayCredential.PAYMENT_URL] value.
     */
    fun init(
        context: Context,
        clientKey: String,
        clientPass: String,
        paymentUrl: String,
    ) {
        expresspayStorage = ExpresspayStorage(context)

        setClientKey(clientKey)
        setClientPass(clientPass)
        setPaymentUrl(paymentUrl)
    }

    private fun setClientKey(key: String) {
        _clientKey = key
        expresspayStorage?.setCredential(CLIENT_KEY, key)
    }

    /**
     * Get the [CLIENT_KEY] value.
     * @return [_clientKey].
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun clientKey(): String {
        requireInit()

        if (_clientKey == null) {
            _clientKey = expresspayStorage?.getCredentials(CLIENT_KEY)
        }

        return _clientKey ?: throw ExpresspaySdkIsNotInitializedException()
    }

    private fun setClientPass(password: String) {
        _clientPass = password
        expresspayStorage?.setCredential(CLIENT_PASS, password)
    }

    /**
     * Get the [CLIENT_PASS] value.
     * @return [_clientPass].
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun clientPass(): String {
        requireInit()

        if (_clientPass == null) {
            _clientPass = expresspayStorage?.getCredentials(CLIENT_PASS)
        }

        return _clientPass ?: throw ExpresspaySdkIsNotInitializedException()
    }

    private fun setPaymentUrl(paymentUrl: String) {
        _paymentUrl = paymentUrl
        expresspayStorage?.setCredential(PAYMENT_URL, paymentUrl)
    }

    /**
     * Get the [PAYMENT_URL] value.
     * @return [_paymentUrl].
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun paymentUrl(): String {
        requireInit()

        if (_paymentUrl == null) {
            _paymentUrl = expresspayStorage?.getCredentials(PAYMENT_URL)
        }

        return _paymentUrl ?: throw ExpresspaySdkIsNotInitializedException()
    }

    /**
     * Soft check of the [ExpresspayCredential] initialization status.
     * @return the true if all [ExpresspayCredential] values are provided.
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun isInitialized(): Boolean {
        if (_isInitialized == null) {
            _isInitialized =
                !_clientKey.isNullOrEmpty() && !_clientPass.isNullOrEmpty() && !_paymentUrl.isNullOrEmpty()
        }

        return _isInitialized ?: throw ExpresspaySdkIsNotInitializedException()
    }

    /**
     * Hard check of the [ExpresspayCredential] initialization status.
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun requireInit() {
        if (!isInitialized()) {
            throw ExpresspaySdkIsNotInitializedException()
        }
    }
}
