package com.edfapaygw.sdk.views.edfacardpay

import com.edfapaygw.sdk.core.EdfaPgSdk
import com.edfapaygw.sdk.model.response.base.error.EdfaPgError
import com.edfapaygw.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsCallback
import com.edfapaygw.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResponse
import com.edfapaygw.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResult


internal fun EdfaPgSaleWebRedirectActivity.checkTransactionStatus(transactionData:CardTransactionData){
    EdfaPgSdk.Adapter.GET_TRANSACTION_DETAILS.execute(
        transactionData.response!!.transactionId,
        transactionData.payer.email,
        transactionData.card.number,
        callback = handleSaleResponse()
    )

}

internal fun EdfaPgSaleWebRedirectActivity.handleSaleResponse() : EdfaPgGetTransactionDetailsCallback{
    return object : EdfaPgGetTransactionDetailsCallback {
        override fun onResponse(response: EdfaPgGetTransactionDetailsResponse) {
            super.onResponse(response)
        }

        override fun onResult(result: EdfaPgGetTransactionDetailsResult) {
            when (result) {
                is EdfaPgGetTransactionDetailsResult.Success -> {
                    operationCompleted(result.result, null)
                }
            }
        }

        override fun onError(error: EdfaPgError) {
            operationCompleted(null, error)
        }

        override fun onFailure(throwable: Throwable) {
            super.onFailure(throwable)
        }
    }
}