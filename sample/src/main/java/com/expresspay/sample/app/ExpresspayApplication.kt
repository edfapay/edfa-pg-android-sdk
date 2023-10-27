/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.app

import android.app.Application
import com.edfapg.sdk.core.EdfaPgSdk
import io.kimo.lib.faker.Faker

class EdfaPgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)
        EdfaPgSdk.init(this)
    }
}
