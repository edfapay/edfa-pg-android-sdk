/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.edfapg.sample.ui.BaseActivity
import com.edfapg.sdk.core.EdfaPgSdk
import io.kimo.lib.faker.Faker
import java.util.Locale


class EdfaPgApplication : Application() {
    private val PAYMENT_URL = "https://api.edfapay.com/payment/post" // edfapay
    private val PAYMENT_URL_DEV = "https://apidev.edfapay.com/payment/post" // edfapay

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)

        EdfaPgSdk.init(
            this,
            clientKey = "MERCHANT_KEY",
            clientPass = "MERCHANT_PASSWORD",
            paymentUrl = PAYMENT_URL,
        )


        EdfaPgSdk.enableDebug()

        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        BaseActivity.dLocale = Locale("ar-SA")
    }
}
