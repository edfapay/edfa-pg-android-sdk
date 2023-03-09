/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sample.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.expresspay.sample.R
import com.expresspay.sample.app.ExpresspayTransactionStorage
import com.expresspay.sample.app.preattyPrint
import com.expresspay.sample.databinding.ActivitySaleBinding
import com.expresspay.sdk.core.ExpresspaySdk
import com.expresspay.sdk.model.request.card.ExpresspayTestCard
import com.expresspay.sdk.model.request.options.ExpresspaySaleOptions
import com.expresspay.sdk.model.request.order.ExpresspaySaleOrder
import com.expresspay.sdk.model.request.payer.ExpresspayPayer
import com.expresspay.sdk.model.request.payer.ExpresspayPayerOptions
import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.sale.ExpresspaySaleCallback
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResult
import io.kimo.lib.faker.Faker
import java.text.DecimalFormat
import java.util.*

class ExpresspaySaleActivity : AppCompatActivity(R.layout.activity_sale) {

    private lateinit var binding: ActivitySaleBinding
    private lateinit var expresspayTransactionStorage: ExpresspayTransactionStorage

    private val random = Random()

    private var payerBirthdate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        expresspayTransactionStorage = ExpresspayTransactionStorage(this)
        binding = ActivitySaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureView()
    }

    private fun configureView() {
        binding.btnClearTransactions.setOnClickListener {
            expresspayTransactionStorage.clearTransactions()
        }
        binding.btnRandomizeRequired.setOnClickListener {
            randomize(false)
        }
        binding.btnRandomizeAll.setOnClickListener {
            randomize(true)
        }
        binding.etxtPayerBirthdate.setOnClickListener {
            val nowCalendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    payerBirthdate = Calendar.getInstance()
                    payerBirthdate?.set(year, month, dayOfMonth)

                    binding.etxtPayerBirthdate.setText(payerBirthdate?.time.toString())
                },
                nowCalendar.get(Calendar.YEAR),
                nowCalendar.get(Calendar.MONTH),
                nowCalendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        binding.btnAuth.setOnClickListener {
            executeRequest(true)
        }
        binding.btnSale.setOnClickListener {
            executeRequest(false)
        }
    }

    private fun randomize(isAll: Boolean) {
        binding.etxtOrderId.setText(UUID.randomUUID().toString())
        binding.etxtOrderAmount.setText(DecimalFormat("#.##").format(random.nextDouble() * 10_000).replace(",", "."))
        binding.etxtOrderDescription.setText(Faker.Lorem.sentences())
        binding.etxtOrderCurrencyCode.setText("SAR")

        binding.etxtPayerFirstName.setText(Faker.Name.firstName())
        binding.etxtPayerLastName.setText(Faker.Name.lastName())
        binding.etxtPayerAddress.setText(Faker.Address.secondaryAddress())
        binding.etxtPayerCountryCode.setText(Faker.Address.countryAbbreviation())
        binding.etxtPayerCity.setText(Faker.Address.city())
        binding.etxtPayerZip.setText(Faker.Address.zipCode())
        binding.etxtPayerEmail.setText(Faker.Internet.email())
        binding.etxtPayerPhone.setText(Faker.Phone.phoneWithAreaCode())
        binding.etxtPayerIpAddress.setText(
            "${random.nextInt(256)}.${random.nextInt(256)}.${random.nextInt(256)}.${random.nextInt(
                256
            )}"
        )

        binding.rgCard.check(binding.rgCard.children.toList().random().id)
        binding.txtResponse.text = ""

        if (isAll) {
            binding.etxtPayerMiddleName.setText(Faker.Name.lastName())
            binding.etxtPayerAddress2.setText(Faker.Address.streetWithNumber())
            binding.etxtPayerState.setText(Faker.Address.state())

            payerBirthdate?.set(1000 + random.nextInt(2000), random.nextInt(12), random.nextInt(31))
            binding.etxtPayerBirthdate.setText(payerBirthdate?.time.toString())

            binding.cbRecurringInit.isChecked = random.nextBoolean()
            binding.etxtChannelId.setText(UUID.randomUUID().toString().take(16))
        } else {
            binding.etxtPayerMiddleName.setText("")
            binding.etxtPayerAddress2.setText("")
            binding.etxtPayerState.setText("")
            binding.etxtPayerBirthdate.setText("")

            binding.cbRecurringInit.isChecked = false
            binding.etxtChannelId.setText("")
        }
    }

    private fun onRequestStart() {
        binding.progressBar.show()
        binding.txtResponse.text = ""
    }

    private fun onRequestFinish() {
        binding.progressBar.hide()
    }

    private fun executeRequest(isAuth: Boolean) {
        val amount = try {
            binding.etxtOrderAmount.text.toString().toDouble()
        } catch (e: Exception) {
            0.10
        }

        val order = ExpresspaySaleOrder(
            id = binding.etxtOrderId.text.toString(),
            amount = amount,
            currency = binding.etxtOrderCurrencyCode.text.toString(),
            description = binding.etxtOrderDescription.text.toString()
        )

        val card = when (binding.rgCard.checkedRadioButtonId) {
            R.id.rb_card_success -> ExpresspayTestCard.SALE_SUCCESS
            R.id.rb_card_failure -> ExpresspayTestCard.SALE_FAILURE
            R.id.rb_card_capture_failure -> ExpresspayTestCard.CAPTURE_FAILURE
            R.id.rb_card_3ds_success -> ExpresspayTestCard.SECURE_3D_SUCCESS
            R.id.rb_card_3ds_failure -> ExpresspayTestCard.SECURE_3D_FAILURE
            else -> ExpresspayTestCard.SALE_SUCCESS
        }

        val payerOptions = ExpresspayPayerOptions(
            middleName = binding.etxtPayerMiddleName.text.toString(),
            address2 = binding.etxtPayerAddress2.text.toString(),
            state = binding.etxtPayerState.text.toString(),
            birthdate = payerBirthdate?.time,
        )
        val payer = ExpresspayPayer(
            firstName = binding.etxtPayerFirstName.text.toString(),
            lastName = binding.etxtPayerLastName.text.toString(),
            address = binding.etxtPayerAddress.text.toString(),
            country = binding.etxtPayerCountryCode.text.toString(),
            city = binding.etxtPayerCity.text.toString(),
            zip = binding.etxtPayerZip.text.toString(),
            email = binding.etxtPayerEmail.text.toString(),
            phone = binding.etxtPayerPhone.text.toString(),
            ip = binding.etxtPayerIpAddress.text.toString(),
            options = payerOptions
        )

        val saleOptions = when (binding.cbRecurringInit.isChecked) {
            true -> ExpresspaySaleOptions(
                channelId = binding.etxtChannelId.text.toString(),
                recurringInit = binding.cbRecurringInit.isChecked
            )
            false -> null
        }

        val transaction = ExpresspayTransactionStorage.Transaction(
            payerEmail = payer.email,
            cardNumber = card.number
        )

        val termUrl3ds = "https://pay.expresspay.sa/"

        onRequestStart()
        ExpresspaySdk.Adapter.SALE.execute(
            order = order,
            card = card,
            payer = payer,
            termUrl3ds = termUrl3ds,
            options = saleOptions,
            auth = isAuth,
            callback = object : ExpresspaySaleCallback {
                override fun onResponse(response: ExpresspaySaleResponse) {
                    super.onResponse(response)
                    onRequestFinish()
                    binding.txtResponse.text = response.preattyPrint()
                }

                override fun onResult(result: ExpresspaySaleResult) {

                    transaction.fill(result.result)
                    transaction.isAuth = isAuth

                    if (result is ExpresspaySaleResult.Recurring) {
                        transaction.recurringToken = result.result.recurringToken
                    } else if (result is ExpresspaySaleResult.Secure3d) {
                        ExpresspayRedirect3dsActivity.open(
                            this@ExpresspaySaleActivity,
                            result.result.redirectParams.termUrl,
                            result.result.redirectUrl,
                            result.result.redirectParams.paymentRequisites,
                            termUrl3ds
                        )
                    }

                    expresspayTransactionStorage.addTransaction(transaction)
                }

                override fun onError(error: ExpresspayError) = Unit

                override fun onFailure(throwable: Throwable) {
                    super.onFailure(throwable)
                    onRequestFinish()
                    binding.txtResponse.text = throwable.preattyPrint()
                }
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ExpresspayRedirect3dsActivity.isOk(requestCode, resultCode)) {
            Toast.makeText(this, "The 3ds operation has been completed.", Toast.LENGTH_SHORT).show()
        }
    }
}
