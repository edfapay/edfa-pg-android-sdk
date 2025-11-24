package com.edfapg.sdk

import Payment2Screen
import Payment3Screen
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.edfapg.sdk.core.transactionCompleted
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.EdfaPayDesignType
import com.edfapg.sdk.toolbox.serializable
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.payment1.Payment1Screen
import kotlinx.coroutines.delay
import java.util.Locale

class PaymentActivity : ComponentActivity() {
    companion object {
        var saleResponse: EdfaPgSaleResponse? = null
    }
    private var isAlreadyShown = false


    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            if (exception is IllegalArgumentException && 
                exception.message?.contains("Only VectorDrawables and rasterized asset types") == true) {
                Log.e("PaymentActivity", "Caught unsupported drawable exception. Resource validation should have prevented this.", exception)
                try {
                    runOnUiThread {
                        finish()
                    }
                } catch (e: Exception) {
                    defaultExceptionHandler?.uncaughtException(thread, exception)
                }
            } else {
                defaultExceptionHandler?.uncaughtException(thread, exception)
            }
        }

        val edfaCardPay = EdfaCardPay.shared()

        val design = intent.getStringExtra("design")
        val locale = intent.getStringExtra("locale")


        locale?.let { updateLocale(it) }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        val sale3dsRedirectLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val result = it.data?.serializable<EdfaPgGetTransactionDetailsSuccess>("result")
                    val error = it.data?.serializable<EdfaPgError>("error")
                    transactionCompleted(result, error, this, saleResponse)
                }
            }

        // Set the content for the activity
        setContent {
            val navController = rememberNavController()

            if (design != null) {
                when (design.lowercase(Locale.getDefault())) {
                    EdfaPayDesignType.one.value -> if (!isAlreadyShown) {
                        isAlreadyShown = true
                        Payment1Screen(
                            navController,
                            edfaCardPay,
                            this,
                            sale3dsRedirectLauncher
                        )
                        LaunchedEffect(true) {
                            delay(100).apply {
                                isAlreadyShown = false
                            }
                        }
                    }

                    EdfaPayDesignType.two.value -> Payment2Screen(
                        navController,
                        edfaCardPay,
                        this,
                        sale3dsRedirectLauncher
                    )

                    EdfaPayDesignType.three.value -> Payment3Screen(
                        navController,
                        edfaCardPay,
                        this,
                        sale3dsRedirectLauncher
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Restore the default exception handler to avoid interfering with other parts of the app
        defaultExceptionHandler?.let {
            Thread.setDefaultUncaughtExceptionHandler(it)
        }
    }

    private fun updateLocale(code: String) {
        val dLocale = Locale(code)

        Locale.setDefault(dLocale)

        val configuration = Configuration()
        configuration.setLocale(dLocale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
