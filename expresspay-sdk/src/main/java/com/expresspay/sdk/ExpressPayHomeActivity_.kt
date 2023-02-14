package com.expresspay.sdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.expresspay.sdk.core.ExpresspaySdk
import com.expresspay.sdk.databinding.ActivityExpressPayHomeBinding
import com.expresspay.sdk.model.request.order.ExpresspaySaleOrder
import com.expresspay.sdk.model.request.payer.ExpresspayPayer
import com.expresspay.sdk.views.expresscardpay.ExpressCardPay
import java.util.*

class ExpressPayHomeActivity_ : AppCompatActivity() {
    private lateinit var binding: ActivityExpressPayHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpressPayHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaleWithCardUi.setOnClickListener {
            ExpresspaySdk.init(
                this,
                "b5abdab4-5c46-11ed-a7be-8e03e789c25f",
                "cdb715a1b482b2af375785d70e8005cd",
                "https://api.expresspay.sa/post"
            )
            payWithCard()
        }
    }

    fun payWithCard(){

        val order = ExpresspaySaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 0.10,
            currency = "SAR",
            description = "Test Order"
        )

        val payer = ExpresspayPayer(
            "Zohaib","Kambrani",
            "Riyadh","SA", "Riyadh","123123",
            "a2zzuhaib@gmail.com","966500409598",
            "171.100.100.123"
        )

        val expressCardPay = ExpressCardPay()
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
        expressCardPay.initialize(
            this,
            onError = {

            },
            onPresent = {

            }
        )


        /*
        * To get intent of card screen activity to present in your own choice (ready to use)
        * */
//        startActivity(expressCardPay.intent(
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
//        expressCardPay.fragment(
//            onError = {
//
//            },
//            onPresent = {
//
//            }
//        )
    }
}