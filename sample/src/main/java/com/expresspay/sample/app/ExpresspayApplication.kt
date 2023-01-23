/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sample.app

import android.app.Application
import com.expresspay.sdk.core.ExpresspaySdk
import io.kimo.lib.faker.Faker

class ExpresspayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Faker.with(this)
        ExpresspaySdk.init(this)
    }
}
