package com.edfapg.sdk.core

import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgResult.*
import com.edfapg.sdk.model.response.sale.EdfaPgSaleCallback
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResult
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.views.edfacardpay.CardTransactionData
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay

fun handleSaleResponse(cardTransactionData: CardTransactionData): EdfaPgSaleCallback {
    return object : EdfaPgSaleCallback {
        override fun onResponse(response: EdfaPgSaleResponse) {
            println("Sale response received: $response")
            super.onResponse(response)
        }

        override fun onResult(result: EdfaPgSaleResult) {
            when (result) {
                is EdfaPgSaleResult.Recurring -> println("Recurring payment: $result")
                is EdfaPgSaleResult.Secure3d -> println("3D Secure: $result")
                is EdfaPgSaleResult.Redirect -> {
                    println("Redirect: $result")
                    cardTransactionData.response = result.result
                }
                is EdfaPgSaleResult.Decline -> println("Payment declined: $result")
                is EdfaPgSaleResult.Success -> {
                    val successResult = result.result
                    when (successResult.result) {
                        SUCCESS -> println("Payment success: $successResult")
                        ACCEPTED -> println("Payment accepted: $successResult")
                        DECLINED -> println("Payment declined: $successResult")
                        ERROR -> println("Payment error: $successResult")
                        REDIRECT -> TODO()
                    }
                }
            }
        }

        override fun onError(error: EdfaPgError) {
            println("Error during sale: ${error.message}")
            EdfaCardPay.shared()?._onTransactionFailure?.invoke(null, error)
        }

        override fun onFailure(throwable: Throwable) {
            println("Failure during sale: ${throwable.message}")
            EdfaCardPay.shared()?._onTransactionFailure?.invoke(null, throwable)
        }
    }
}
