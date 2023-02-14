/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.core

import android.content.Context
import android.content.pm.PackageManager
import com.expresspay.sdk.feature.adapter.ExpresspayCaptureAdapter
import com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter
import com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter
import com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter
import com.expresspay.sdk.feature.adapter.ExpresspayRecurringSaleAdapter
import com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter

/**
 * The initial point of the [ExpresspaySdk].
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
 * [ExpresspayCredential.CLIENT_KEY] - Unique key to identify the account in Payment Platform
 * (used as request parameter).
 * [ExpresspayCredential.CLIENT_PASS] - Password for Client authentication in Payment Platform
 * (used for calculating hash parameter).
 * [ExpresspayCredential.PAYMENT_URL] - URL to request the Payment Platform.
 * @see com.expresspay.sdk.feature.adapter.ExpresspayBaseAdapter
 * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil.md5
 *
 * For the transaction, you must send the server to server HTTPS POST request with fields listed below to Payment
 * Platform URL ([ExpresspayCredential.PAYMENT_URL]). In response Payment Platform will return the JSON  encoded string.
 * If your account supports 3D-Secure and credit card supports 3D-Secure, then Payment Platform will return the link to
 * the 3D-Secure Access Control Server to perform 3D-Secure verification. In this case, you need to redirect the
 * cardholder at this link. If there are also some parameters except the link in the result, you will need to redirect
 * the cardholder at this link together with the parameters using the method of data transmitting indicated in the same
 * result. In the case of 3D-Secure after verification on the side of the 3D-Secure server, the owner of a credit card
 * will come back to your site using the link you specify in the sale request, and Payment Platform will return the
 * result of transaction processing to the Notification URL action.
 *
 * To initialize the [ExpresspaySdk] call one of the following methods: [init].
 * The initialization can be done programmatically or through the Application AndroidManifest.xml.
 * [ExpresspaySdk] requires the Internet permission: <uses-permission android:name="android.permission.INTERNET" />
 * In case the [ExpresspaySdk] is not initialized the [ExpresspaySdkIsNotInitializedException] will be thrown.
 *
 * To test/simulate the Platon Payment System use [com.expresspay.sdk.model.request.card.ExpresspayTestCard].
 */
object ExpresspaySdk {

    /**
     * Initialize the [ExpresspaySdk] implicitly using the meta-data of the AndroidManifest.xml
     *
     * @param context the app context.
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun init(context: Context) {
        val metaData = context
            .packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData

        ExpresspayCredential.init(context, metaData)
        ExpresspayCredential.requireInit()
    }

    /**
     * Initialize the [ExpresspaySdk] explicitly using the [ExpresspayCredential] values.
     *
     * @param context the app context.
     * @param clientKey the [ExpresspayCredential.CLIENT_KEY] value.
     * @param clientPass the [ExpresspayCredential.CLIENT_PASS] value.
     * @param paymentUrl the [ExpresspayCredential.PAYMENT_URL] value.
     * @throws ExpresspaySdkIsNotInitializedException
     */
    fun init(
        context: Context,
        clientKey: String,
        clientPass: String,
        paymentUrl: String,
    ) {
        ExpresspayCredential.init(context, clientKey, clientPass, paymentUrl)
        ExpresspayCredential.requireInit()
    }

    /**
     * Indicating of the [ExpresspaySdk] initialize status.
     */
    fun isInitialized(): Boolean = ExpresspayCredential.isInitialized()

    /**
     * The [com.expresspay.sdk.feature.adapter]s holder to check the [ExpresspaySdk] initialization status before use.
     * @throws ExpresspaySdkIsNotInitializedException
     */
    object Adapter {
        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.RECURRING_SALE] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val RECURRING_SALE: ExpresspayRecurringSaleAdapter = ExpresspayRecurringSaleAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.SALE] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val SALE: ExpresspaySaleAdapter = ExpresspaySaleAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.CAPTURE] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val CAPTURE: ExpresspayCaptureAdapter = ExpresspayCaptureAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.CREDITVOID] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val CREDITVOID: ExpresspayCreditvoidAdapter = ExpresspayCreditvoidAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.GET_TRANS_STATUS] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val GET_TRANSACTION_STATUS: ExpresspayGetTransactionStatusAdapter = ExpresspayGetTransactionStatusAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }

        /**
         * Adapter for the [com.expresspay.sdk.model.api.ExpresspayAction.GET_TRANS_DETAILS] request.
         * @throws ExpresspaySdkIsNotInitializedException
         */
        val GET_TRANSACTION_DETAILS: ExpresspayGetTransactionDetailsAdapter = ExpresspayGetTransactionDetailsAdapter
            get() {
                ExpresspayCredential.requireInit()
                return field
            }
    }
}
