/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.core

import android.content.Context
import android.content.pm.PackageManager
import com.edfapaygw.sdk.feature.adapter.EdfaPgCaptureAdapter
import com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter
import com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter
import com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter
import com.edfapaygw.sdk.feature.adapter.EdfaPgRecurringSaleAdapter
import com.edfapaygw.sdk.feature.adapter.EdfaPgSaleAdapter

/**
 * The initial point of the [EdfaPgSdk].
 *
 * Before you get an account to access Payment Platform, you must provide the following data to the Payment Platform
 * administrator:
 * IP list - List of your IP addresses, from which requests to Payment Platform will be sent.
 * Notification URL - URL which will be receiving the notifications of the processing results of your request to
 * Payment Platform.
 * Contact email -  Email address of Responsible Person who will monitor transactions, conduct refunds, etc.
 *
 * Note: Notification URL is mandatory if your account supports 3D-Secure. The length of Notification URL should not be
 * more than 255 symbols.
 * With all Payment Platform POST requests at Notification URL the Merchant must return the string "OK" if he
 * successfully received data or return "ERROR".
 *
 * You should get the following information from administrator to begin working with the Payment Platform:
 * [EdfaPgCredential.CLIENT_KEY] - Unique key to identify the account in Payment Platform
 * (used as request parameter).
 * [EdfaPgCredential.CLIENT_PASS] - Password for Client authentication in Payment Platform
 * (used for calculating hash parameter).
 * [EdfaPgCredential.PAYMENT_URL] - URL to request the Payment Platform.
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgBaseAdapter
 * @see com.edfapaygw.sdk.toolbox.EdfaPgHashUtil.md5
 *
 * For the transaction, you must send the server to server HTTPS POST request with fields listed below to Payment
 * Platform URL ([EdfaPgCredential.PAYMENT_URL]). In response Payment Platform will return the JSON  encoded string.
 * If your account supports 3D-Secure and credit card supports 3D-Secure, then Payment Platform will return the link to
 * the 3D-Secure Access Control Server to perform 3D-Secure verification. In this case, you need to redirect the
 * cardholder at this link. If there are also some parameters except the link in the result, you will need to redirect
 * the cardholder at this link together with the parameters using the method of data transmitting indicated in the same
 * result. In the case of 3D-Secure after verification on the side of the 3D-Secure server, the owner of a credit card
 * will come back to your site using the link you specify in the sale request, and Payment Platform will return the
 * result of transaction processing to the Notification URL action.
 *
 * To initialize the [EdfaPgSdk] call one of the following methods: [init].
 * The initialization can be done programmatically or through the Application AndroidManifest.xml.
 * [EdfaPgSdk] requires the Internet permission: <uses-permission android:name="android.permission.INTERNET" />
 * In case the [EdfaPgSdk] is not initialized the [EdfaPgSdkIsNotInitializedException] will be thrown.
 *
 * To test/simulate the Platon Payment System use [com.edfapaygw.sdk.model.request.card.EdfaPgTestCard].
 */
object EdfaPgSdk {

    /**
     * Initialize the [EdfaPgSdk] implicitly using the meta-data of the AndroidManifest.xml
     *
     * @param context the app context.
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun init(context: Context) {
        val metaData = context
            .packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData

        EdfaPgCredential.init(context, metaData)
        EdfaPgCredential.requireInit()
    }

    /**
     * Initialize the [EdfaPgSdk] explicitly using the [EdfaPgCredential] values.
     *
     * @param context the app context.
     * @param clientKey the [EdfaPgCredential.CLIENT_KEY] value.
     * @param clientPass the [EdfaPgCredential.CLIENT_PASS] value.
     * @param paymentUrl the [EdfaPgCredential.PAYMENT_URL] value.
     * @throws EdfaPgSdkIsNotInitializedException
     */
    fun init(
        context: Context,
        clientKey: String,
        clientPass: String,
        paymentUrl: String,
    ) {
        EdfaPgCredential.init(context, clientKey, clientPass, paymentUrl)
        EdfaPgCredential.requireInit()
    }

    /**
     * Indicating of the [EdfaPgSdk] initialize status.
     */
    fun isInitialized(): Boolean = EdfaPgCredential.isInitialized()

    /**
     * The [com.edfapaygw.sdk.feature.adapter]s holder to check the [EdfaPgSdk] initialization status before use.
     * @throws EdfaPgSdkIsNotInitializedException
     */
    object Adapter {
        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.RECURRING_SALE] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val RECURRING_SALE: EdfaPgRecurringSaleAdapter = EdfaPgRecurringSaleAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.SALE] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val SALE: EdfaPgSaleAdapter = EdfaPgSaleAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.CAPTURE] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val CAPTURE: EdfaPgCaptureAdapter = EdfaPgCaptureAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.CREDITVOID] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val CREDITVOID: EdfaPgCreditvoidAdapter = EdfaPgCreditvoidAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.GET_TRANS_STATUS] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val GET_TRANSACTION_STATUS: EdfaPgGetTransactionStatusAdapter = EdfaPgGetTransactionStatusAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.edfapaygw.sdk.model.api.EdfaPgAction.GET_TRANS_DETAILS] request.
         * @throws EdfaPgSdkIsNotInitializedException
         */
        val GET_TRANSACTION_DETAILS: EdfaPgGetTransactionDetailsAdapter = EdfaPgGetTransactionDetailsAdapter
            get() {
                EdfaPgCredential.requireInit()
                return field
            }
    }
}
