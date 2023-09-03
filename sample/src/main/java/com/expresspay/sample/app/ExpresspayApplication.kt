/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sample.app

import android.app.Application
import com.edfapaygw.sdk.core.EdfaPgSdk
import io.kimo.lib.faker.Faker

class EdfaPgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)
        EdfaPgSdk.init(this)
    }
}
