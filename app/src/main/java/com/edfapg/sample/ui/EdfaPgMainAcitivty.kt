/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.edfapg.sample.app.EdfaPgTransactionStorage
import com.edfapg.sample.databinding.ActivityMainBinding
import com.edfapg.sdk.EdfaSadadPay
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResult
import com.edfapg.sdk.toolbox.EdfaPayDesignType
import com.edfapg.sdk.toolbox.EdfaPayLanguage
import com.edfapg.sdk.toolbox.serializable
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaPayWithCardDetails
import java.util.UUID

class EdfaPgMainAcitivty : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var edfapayTransactionStorage: EdfaPgTransactionStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edfapayTransactionStorage = EdfaPgTransactionStorage(this)

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

        binding.btnSadadPay.setOnClickListener {
            payWithSadad()
        }
    }

    fun payWithCard() {

        val order = EdfaPgSaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 0.12,
            currency = "SAR",
            description = "Test Order",
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
            .setExtras(listOf())
            .setRecurring(true)
            .setDesignType(EdfaPayDesignType.one)
            .setLanguage(EdfaPayLanguage.en)
            .onTransactionFailure { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Failure", Toast.LENGTH_LONG).show()
            }.onTransactionSuccess { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Success", Toast.LENGTH_LONG).show()
            }
        println(
            "recurring:: ${edfaCardPay._recurring}"
        )

        /*
        * Precise way to start card payment (ready to use)
        * */
        edfaCardPay.initialize(
            this,
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
            description = "Test Order",
        )

        val payer = EdfaPgPayer(
            "Zohaib", "Kambrani",
            "Riyadh", "SA", "Riyadh", "123123",
            "kashifuop99@gmail.com", "966500409598",
            "171.100.100.123"
        )

//        val card = com.edfapg.sample.app.LIVE_CARD3 // This from com.edfapg.sample.app.Credential.kt (.gitignore)
        val card = EdfaPgCard("4458xxxxxxxx8293", 1, 2090, "123")
        EdfaPayWithCardDetails(this)
            .setOrder(order)
            .setPayer(payer)
            .setExtras(listOf())
            .setCard(card)
            .setRecurring(true)
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

    fun payWithSadad(){
        EdfaSadadPay()
            .setOrderId(UUID.randomUUID().toString())
            .setOrderAmount(25.0)
            .setOrderDescription("Test ss 200")
            .setCustomerName("waddah s2s")
            .setMobileNumber("0539662569")
            .onSuccess { response ->
                Log.i("onSuccess", response.toString())

            }
            .onFailure { response, exception ->
                Log.e("onFailure", response?.message ?: exception.message ?: "STWR")

            }
            .initialize { errors ->
                Log.e("onError", errors.toString())

            }

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

