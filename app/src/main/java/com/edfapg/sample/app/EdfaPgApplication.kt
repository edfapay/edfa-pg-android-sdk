/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.webkit.WebView
import com.edfapg.sdk.core.EdfaPgSdk
import io.kimo.lib.faker.Faker


class EdfaPgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)

        EdfaPgSdk.init(
            this,
            "f6534bd9-d4e8-431f-93d2-592b1b112e9b",
            "59338f04447a23f15f749a4bd3bb0e9f",
            "https://api.edfapay.com/payment/post"
        )

        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }
}