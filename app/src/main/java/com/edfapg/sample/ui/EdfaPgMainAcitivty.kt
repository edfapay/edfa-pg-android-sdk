/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.edfapg.sample.app.SaleOrderValidator
import com.edfapg.sample.databinding.ActivityMainBinding
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.toolbox.DesignType
import com.edfapg.sdk.toolbox.EdfaLocale
import com.edfapg.sdk.toolbox.serializable
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaPayWithCardDetails
import java.util.UUID

class EdfaPgMainAcitivty : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureView()
    }

    private fun configureView() {
        binding.btnSale.setOnClickListener {
            startActivity(Intent(this, EdfaPgSaleActivity::class.java))
        }
        binding.btnRecurringSale.setOnClickListener {
            startActivity(Intent(this, EdfaPgRecurringSaleActivity::class.java))
        }
        binding.btnCapture.setOnClickListener {
            startActivity(Intent(this, EdfaPgCaptureActivity::class.java))
        }
        binding.btnCreditVoid.setOnClickListener {
            startActivity(Intent(this, EdfaPgCreditvoidActivity::class.java))
        }
        binding.btnGetTransStatus.setOnClickListener {
            startActivity(Intent(this, EdfaPgGetTransStatusActivity::class.java))
        }
        binding.btnGetTransDetails.setOnClickListener {
            startActivity(Intent(this, EdfaPgGetTransDetailsActivity::class.java))
        }

        binding.btnSaleWithCardUi.setOnClickListener {
            payWithCard()
//            payWithCardDetails()
        }
    }

    fun payWithCard() {

        val order = EdfaPgSaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 0.12,
            currency = "SAR",
            description = "Test Order"
        )


        val payer = EdfaPgPayer(
            "Zohaib", "Kambrani",
            "Riyadh", "SA", "Riyadh", "123123",
            "a2zzuhaib@gmail.com", "966500409598",
            "171.100.100.123"
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
            DesignType.PAYMENT_DESIGN_2,//change to the desired Ui variant here
            EdfaLocale.EN, //change to desired locale here
            onError = {
                Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
            },
            onPresent = {
            }
        )
    }

    fun payWithCardDetails() {

        val order = EdfaPgSaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 0.12,
            currency = "SAR",
            description = "Test Order"
        )

        val payer = EdfaPgPayer(
            "Zohaib", "Kambrani",
            "Riyadh", "SA", "Riyadh", "123123",
            "kashifuop99@gmail.com", "966500409598",
            "171.100.100.123"
        )

//        val card = EdfaPgCard("4458271329748293", 7, 2029, "331")
//        val card = EdfaPgCard("5452057473989962", 3, 2026, "386")
        val card = EdfaPgCard("4890222013171587", 9, 2029, "826")
        EdfaPayWithCardDetails(this)
            .setOrder(order)
            .setPayer(payer)
            .setCard(card)
            .onTransactionFailure { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Failure", Toast.LENGTH_LONG).show()
            }.onTransactionSuccess { res, data ->

                print("$res $data")
                Toast.makeText(this, "Transaction Success", Toast.LENGTH_LONG).show()
            }
            .initialize(
                onError = {
                    Toast.makeText(this, "onError $it", Toast.LENGTH_LONG).show()
                },
                onPresent = {

                }
            )


    }

    val sale3dsRedirectLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.serializable<EdfaPgGetTransactionDetailsSuccess>("result")
                val error = it.data?.serializable<EdfaPgError>("error")
//            transactionCompleted(result, error)
            }
        }


}

