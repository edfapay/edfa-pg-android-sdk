package com.edfapg.sdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.payment1.Payment1Screen

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var xpressCardPay = EdfaCardPay.shared()

        // Add the back press callback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Define what happens when the back button is pressed
                finish()  // Close the activity and go back to the previous one
            }
        })
        setContent {
            val navController = rememberNavController()
            Payment1Screen(navController,xpressCardPay)  // Call your composable here
        }
    }

}
