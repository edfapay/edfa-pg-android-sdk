package com.edfapg.sdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.edfapg.sdk.core.EdfaPgSdk
import com.edfapg.sdk.databinding.ActivityEdfaPayHomeBinding
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import java.util.*

class EdfaPgHomeActivity_ : AppCompatActivity() {
    private lateinit var binding: ActivityEdfaPayHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdfaPayHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaleWithCardUi.setOnClickListener {
            EdfaPgSdk.init(
                this,
                "b5abdab4-5c46-11ed-a7be-8e03e789c25f",
                "cdb715a1b482b2af375785d70e8005cd",
                "https://api.expresspay.sa/post"
            )
            payWithCard()
        }
    }

    fun payWithCard(){

        val order = EdfaPgSaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 0.10,
            currency = "SAR",
            description = "Test Order"
        )

        val payer = EdfaPgPayer(
            "Zohaib","Kambrani",
            "Riyadh","SA", "Riyadh","123123",
            "a2zzuhaib@gmail.com","966500409598",
            "171.100.100.123",
            null
        )

        val edfaCardPay = EdfaCardPay()
            .setOrder(order)
            .setPayer(payer)
            .onTransactionFailure { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Failure", Toast.LENGTH_LONG).show()
            }.onTransactionSuccess { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Success", Toast.LENGTH_LONG).show()
            }

        /*
        * Precise way to start card payment (ready to use)
        * */
        edfaCardPay.initialize(
            this,
            onError = {

            },
            onPresent = {

            }
        )


        /*
        * To get intent of card screen activity to present in your own choice (ready to use)
        * */
//        startActivity(edfaCardPay.intent(
//            this,
//            onError = {
//
//            },
//            onPresent = {
//
//            })
//        )


        /*
        * To get fragment of card screen to present in your own choice (ready to use)
        * */
//        edfaCardPay.fragment(
//            onError = {
//
//            },
//            onPresent = {
//
//            }
//        )
    }
}