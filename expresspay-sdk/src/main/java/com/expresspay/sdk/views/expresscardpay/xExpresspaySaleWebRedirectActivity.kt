package com.expresspay.sdk.views.expresscardpay

import com.expresspay.sdk.core.ExpresspaySdk
import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsCallback
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResponse
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResult


internal fun ExpresspaySaleWebRedirectActivity.checkTransactionStatus(transactionData:CardTransactionData){
    ExpresspaySdk.Adapter.GET_TRANSACTION_DETAILS.execute(
        transactionData.response!!.transactionId,
        transactionData.payer.email,
        transactionData.card.number,
        callback = handleTxnDetailsResponse()
    )

}

internal fun ExpresspaySaleWebRedirectActivity.handleTxnDetailsResponse() : ExpresspayGetTransactionDetailsCallback{
    return object : ExpresspayGetTransactionDetailsCallback {
        override fun onResponse(response: ExpresspayGetTransactionDetailsResponse) {
            super.onResponse(response)
        }

        override fun onResult(result: ExpresspayGetTransactionDetailsResult) {
            when (result) {
                is ExpresspayGetTransactionDetailsResult.Success -> {
                    operationCompleted(result.result, null)
                }
            }
        }

        override fun onError(error: ExpresspayError) {
            operationCompleted(null, error)
        }

        override fun onFailure(throwable: Throwable) {
            super.onFailure(throwable)
        }
    }
}