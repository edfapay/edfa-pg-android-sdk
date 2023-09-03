/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.core

import android.content.Context
import android.os.Bundle
import com.edfapaygw.sdk.core.EdfaPgCredential.init

/**
 * The [EdfaPgSdk] credentials holder.
 * It stores and retrieve it automatically after the first successful [init].
 * [EdfaPgCredential] stores values in the [EdfaPgStorage].
 * @throws EdfaPgSdkIsNotInitializedException if [EdfaPgSdk] is not initialized.
 */
internal object EdfaPgCredential {

    /**
     * Unique key to identify the account in Payment Platform (used as request parameter).
     */
    private const val CLIENT_KEY = "com.edfapaygw.sdk.CLIENT_KEY"

    /**
     * Password for Client authentication in Payment Platform (used for calculating hash parameter).
     */
    private const val CLIENT_PASS = "com.edfapaygw.sdk.CLIENT_PASS"

    /**
     * URL to request the Payment Platform.
     */
    private const val PAYMENT_URL = "com.edfapaygw.sdk.PAYMENT_URL"

    private var _clientKey: String? = null
    private var _clientPass: String? = null
    private var _paymentUrl: String? = null

    private var _isInitialized: Boolean? = null
    private var edfapayStorage: EdfaPgStorage? = null

    /**
     * Initialize the [EdfaPgCredential] values.
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
     * Initialize the [EdfaPgCredential] values.
     *
     * @param context the app context.
     * @param clientKey the [EdfaPgCredential.CLIENT_KEY] value.
     * @param clientPass the [EdfaPgCredential.CLIENT_PASS] value.
     * @param paymentUrl the [EdfaPgCredential.PAYMENT_URL] value.
     */
    fun init(
        context: Context,
        clientKey: String,
        clientPass: String,
        paymentUrl: String,
    ) {
        edfapayStorage = EdfaPgStorage(context)

        setClientKey(clientKey)
        setClientPass(clientPass)
        setPaymentUrl(paymentUrl)
    }

    private fun setClientKey(key: String) {
        _clientKey = key
        edfapayStorage?.setCredential(CLIENT_KEY, key)
    }

    /**
     * Get the [CLIENT_KEY] value.
     * @return [_clientKey].
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun clientKey(): String {
        requireInit()

        if (_clientKey == null) {
            _clientKey = edfapayStorage?.getCredentials(CLIENT_KEY)
        }

        return _clientKey ?: throw EdfaPgSdkIsNotInitializedException()
    }

    private fun setClientPass(password: String) {
        _clientPass = password
        edfapayStorage?.setCredential(CLIENT_PASS, password)
    }

    /**
     * Get the [CLIENT_PASS] value.
     * @return [_clientPass].
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun clientPass(): String {
        requireInit()

        if (_clientPass == null) {
            _clientPass = edfapayStorage?.getCredentials(CLIENT_PASS)
        }

        return _clientPass ?: throw EdfaPgSdkIsNotInitializedException()
    }

    private fun setPaymentUrl(paymentUrl: String) {
        _paymentUrl = paymentUrl
        edfapayStorage?.setCredential(PAYMENT_URL, paymentUrl)
    }

    /**
     * Get the [PAYMENT_URL] value.
     * @return [_paymentUrl].
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun paymentUrl(): String {
        requireInit()

        if (_paymentUrl == null) {
            _paymentUrl = edfapayStorage?.getCredentials(PAYMENT_URL)
        }

        return _paymentUrl ?: throw EdfaPgSdkIsNotInitializedException()
    }

    /**
     * Soft check of the [EdfaPgCredential] initialization status.
     * @return the true if all [EdfaPgCredential] values are provided.
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun isInitialized(): Boolean {
        if (_isInitialized == null) {
            _isInitialized =
                !_clientKey.isNullOrEmpty() && !_clientPass.isNullOrEmpty() && !_paymentUrl.isNullOrEmpty()
        }

        return _isInitialized ?: throw EdfaPgSdkIsNotInitializedException()
    }

    /**
     * Hard check of the [EdfaPgCredential] initialization status.
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun requireInit() {
        if (!isInitialized()) {
            throw EdfaPgSdkIsNotInitializedException()
        }
    }
}
