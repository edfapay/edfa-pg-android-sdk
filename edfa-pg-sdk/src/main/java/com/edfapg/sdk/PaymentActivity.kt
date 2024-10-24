package com.edfapg.sdk

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.edfapg.sdk.core.transactionCompleted
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.serializable
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.payment1.Payment1Screen
import com.example.paymentgatewaynew.payment2.Payment2Screen
import com.example.paymentgatewaynew.payment3.Payment3Screen
import java.util.Locale

class PaymentActivity : ComponentActivity() {
    companion object {
        var saleResponse: EdfaPgSaleResponse? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val xpressCardPay = EdfaCardPay.shared()

        val intentData = intent.getStringExtra("paymentDesign")
        val locale = intent.getStringExtra("locale")

        locale?.let { updateLocale(it) }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        val sale3dsRedirectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.serializable<EdfaPgGetTransactionDetailsSuccess>("result")
                val error = it.data?.serializable<EdfaPgError>("error")
                transactionCompleted(result, error, this, saleResponse)
            }
        }

        // Set the content for the activity
        setContent {
            val navController = rememberNavController()
            if (intentData != null) {
                when (intentData.toLowerCase()) {
                    "1" -> Payment1Screen(navController, xpressCardPay, this, sale3dsRedirectLauncher)
                    "2" -> Payment2Screen(navController, xpressCardPay, this, sale3dsRedirectLauncher)
                    "3" -> Payment3Screen(navController, xpressCardPay, this, sale3dsRedirectLauncher)
                }
            }
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
