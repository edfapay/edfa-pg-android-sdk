package com.edfapg.sdk.views.edfacardpay

import com.edfapg.sdk.core.EdfaPgSdk
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.base.error.EdfaPgExactError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsCallback
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResponse
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResult
import com.edfapg.sdk.views.edfacardpay.creditcardview.extensions.isNumeric


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

            if(throwable.message?.isNumeric() == true)
                operationCompleted(
                    null,
                    EdfaPgError(
                        EdfaPgResult.ERROR,
                        throwable.message?.toInt() ?: -1,
                        "",
                        listOf(
                            EdfaPgExactError(throwable.message?.toInt() ?: -1, throwable.message ?: "")
                        )
                    )
                )
            else
                operationCompleted(
                    null,
                    EdfaPgError(
                        EdfaPgResult.ERROR,
                        -1,
                        "",
                        listOf(
                            EdfaPgExactError(-1, throwable.message ?: "")
                        )
                    )
                )
        }
    }
}