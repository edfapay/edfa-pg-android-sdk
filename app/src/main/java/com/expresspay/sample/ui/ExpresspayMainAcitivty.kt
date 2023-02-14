/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.expresspay.sample.databinding.ActivityMainBinding
import com.expresspay.sdk.model.request.order.*
import com.expresspay.sdk.model.request.payer.*
import com.expresspay.sdk.views.expresscardpay.*
import java.util.UUID

class ExpresspayMainAcitivty : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureView()
    }

    private fun configureView() {
        binding.btnSale.setOnClickListener {
            startActivity(Intent(this, ExpresspaySaleActivity::class.java))
        }
        binding.btnRecurringSale.setOnClickListener {
            startActivity(Intent(this, ExpresspayRecurringSaleActivity::class.java))
        }
        binding.btnCapture.setOnClickListener {
            startActivity(Intent(this, ExpresspayCaptureActivity::class.java))
        }
        binding.btnCreditVoid.setOnClickListener {
            startActivity(Intent(this, ExpresspayCreditvoidActivity::class.java))
        }
        binding.btnGetTransStatus.setOnClickListener {
            startActivity(Intent(this, ExpresspayGetTransStatusActivity::class.java))
        }
        binding.btnGetTransDetails.setOnClickListener {
            startActivity(Intent(this, ExpresspayGetTransDetailsActivity::class.java))
        }

        binding.btnSaleWithCardUi.setOnClickListener {
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
