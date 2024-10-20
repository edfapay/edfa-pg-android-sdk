package com.edfapg.sdk

import android.app.Activity
import android.os.Bundle
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

class PaymentActivity : ComponentActivity() {
    companion object {
        // This is a static variable
        var saleResponse: EdfaPgSaleResponse? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var xpressCardPay = EdfaCardPay.shared()
        // Companion object to hold static variables
        val intentData = intent.getStringExtra("paymentDesign")

        // Add the back press callback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Define what happens when the back button is pressed
                finish()  // Close the activity and go back to the previous one
            }
        })
        val sale3dsRedirectLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.serializable<EdfaPgGetTransactionDetailsSuccess>("result")
                val error = it.data?.serializable<EdfaPgError>("error")
                transactionCompleted(result, error,this,saleResponse)
            }
        }

        setContent {
            val navController = rememberNavController()
            if (intentData != null) {
                // Call your composable here
                if(intentData.equals("1",ignoreCase = true)){
                    Payment1Screen(navController,xpressCardPay,this,sale3dsRedirectLauncher)  // Call your composable here
                }else if(intentData.equals("2",ignoreCase = true)){
                    Payment2Screen(navController,xpressCardPay,this,sale3dsRedirectLauncher)  // Call your composable here
                }else if(intentData.equals("3",ignoreCase = true)){
                    Payment3Screen(navController,xpressCardPay,this, sale3dsRedirectLauncher)
                }
            }
        }
    }

}
