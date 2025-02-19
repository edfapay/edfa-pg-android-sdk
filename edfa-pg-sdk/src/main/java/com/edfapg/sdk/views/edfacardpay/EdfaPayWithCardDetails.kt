package com.edfapg.sdk.views.edfacardpay

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.core.EdfaPgSdk
import com.edfapg.sdk.core.handleSaleResponse
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.options.EdfaPgSaleOptions
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.EdfaPgUtil


class EdfaPayWithCardDetails(private val context: Context) : EdfaCardPay() {
    private var saleResponse: EdfaPgSaleResponse? = null

    override fun initialize(
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ) {
        super.initialize(onError, onPresent)
        doSaleTransaction()
    }

    private fun doSaleTransaction() {
        val order = _order
        val payer = _payer
        val card = _card
        val recurring = _recurring

        println("recurringInit::EdfaPayWithCardDetails ${recurring}")


        if (order != null && payer != null && card != null) {
            saleResponse = null
            EdfaPgSdk.Adapter.SALE.execute(
                order = order,
                card = card,
                payer = payer,
                termUrl3ds = EdfaPgUtil.ProcessCompleteCallbackUrl,
                options = recurring,
                auth = false,
                callback = handleSaleResponse(
                    CardTransactionData(
                        order,
                        payer,
                        card,
                        null
                    ),
                    { response, cardData -> // Success callback
                        PaymentActivity.saleResponse = response
                        val intent = EdfaPgSaleWebRedirectActivity.intent(context = context, cardData){ result, error ->
                        transactionCompleted(result, error)
                    }
                        context.startActivity(intent)
                    },
                    { error -> // Failure callback
                        // Handle the error, e.g., show a message to the user
                        if (error != null) {
                            println("Transaction failed: ${error.message}")
                            // Show error to the user
                        } else {
                            println("Transaction was declined.")
                            // Show a decline message
                        }
                    }
                )
            )
        } else {
            println("Something was empty")
        }
    }

    private fun transactionCompleted(
        result: EdfaPgGetTransactionDetailsSuccess?,
        error: EdfaPgError?
    ) {
        if(error != null)
          _onTransactionFailure?.let { failure -> failure(saleResponse, error) }

        else if(result == null)
            _onTransactionFailure?.let { failure -> failure(saleResponse, error) }

        else
            with(result) {
                when (status) {
                    EdfaPgStatus.SETTLED -> _onTransactionSuccess?.let { success -> success(saleResponse, result) }
                    else -> _onTransactionFailure?.let { failure -> failure(saleResponse, result) }

//                EdfaPgStatus.SECURE_3D -> TODO()
//                EdfaPgStatus.REDIRECT -> TODO()
//                EdfaPgStatus.PENDING -> TODO()
//                EdfaPgStatus.REVERSAL -> TODO()
//                EdfaPgStatus.REFUND -> TODO()
//                EdfaPgStatus.CHARGEBACK -> TODO()
//                EdfaPgStatus.DECLINED -> TODO()
                }
            }
    }
}
