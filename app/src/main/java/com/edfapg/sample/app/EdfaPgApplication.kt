/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.edfapg.sample.BuildConfig.MERCHANT_KEY
import com.edfapg.sample.BuildConfig.MERCHANT_PASSWORD
import com.edfapg.sample.BuildConfig.PAYMENT_URL
import com.edfapg.sample.ui.BaseActivity
import com.edfapg.sdk.core.EdfaPgSdk
import io.kimo.lib.faker.Faker
import java.util.Locale


class EdfaPgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)

        EdfaPgSdk.init(
            this,
            clientKey = MERCHANT_KEY,
            clientPass = MERCHANT_PASSWORD,
            paymentUrl = PAYMENT_URL,
        )

        EdfaPgSdk.setAnimationDelay(2000)
        EdfaPgSdk.setFailureAnimation("https://lottie.host/embed/6391d446-ad05-4ec8-bbc3-6e4b995b80c1/MRQIViE7eS.lottie")
        EdfaPgSdk.setSuccessAnimation("https://lottie.host/embed/6d93db21-e1e4-4ac3-9474-aa8182cadba7/vWVmKBU70n.lottie")


        EdfaPgSdk.enableDebug()

        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        BaseActivity.dLocale = Locale("ar-SA")
    }
}
