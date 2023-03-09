package com.expresspay.sdk.views.expresscardpay

import com.expresspay.sdk.core.ExpresspaySdk
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.request.card.ExpresspayCard
import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsSuccess
import com.expresspay.sdk.model.response.sale.ExpresspaySaleCallback
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResult
import com.expresspay.sdk.toolbox.ExpresspayUtil
import com.expresspay.sdk.views.expresscardpay.creditcardview.models.CreditCard


internal fun ExpressCardPayFragment.doSaleTransaction(cardDetail: CreditCard?){
    val order = xpressCardPay?._order
    val payer = xpressCardPay?._payer
    if(order != null && payer != null && cardDetail != null){

        val month = cardDetail.expiryMonth()
        val year = cardDetail.expiryYear()
        if(month == null || year == null)
            return

        val card = ExpresspayCard(cardDetail.unformattedNumber, month, year, cardDetail.cvv)

        saleResponse = null
        ExpresspaySdk.Adapter.SALE.execute(
            order = order,
            card = card,
            payer = payer,
            termUrl3ds = ExpresspayUtil.ProcessCompleteCallbackUrl,
            options = null,
            auth = false,
            callback = handleSaleResponse(CardTransactionData(order, payer, card, null))
        )
    }

}

internal fun ExpressCardPayFragment.handleSaleResponse(cardTransactionData:CardTransactionData) : ExpresspaySaleCallback{
    return object : ExpresspaySaleCallback {
        override fun onResponse(response: ExpresspaySaleResponse) {
            saleResponse = response
            super.onResponse(response)
        }

        override fun onResult(result: ExpresspaySaleResult) {

            val saleResult = result

            if (result is ExpresspaySaleResult.Recurring) {
                print(">> ExpressPaySaleResult.Recurring")
                print(">> $saleResult")

            } else if (result is ExpresspaySaleResult.Secure3d) {
                print(">> ExpressPaySaleResult.Secure3d")
                print(">> $saleResult")

            } else if (result is ExpresspaySaleResult.Redirect) {
                print(">> ExpressPaySaleResult.Redirect")
                print(">> $saleResult")

                cardTransactionData.response = result.result
                val intent = ExpresspaySaleWebRedirectActivity.intent(activity!!, cardTransactionData)
                sale3dsRedirectLauncher.launch(intent)

            } else if (result is ExpresspaySaleResult.Decline) {
                print(">> ExpressPaySaleResult.Decline")
                print(">> $saleResult")

            } else if (result is ExpresspaySaleResult.Success) {

                print(">> ExpressPaySaleResult.Success")
                print(">> $saleResult")

                val successResult = result.result
                if(result.result.result == ExpresspayResult.SUCCESS) {
                    print(">> >> ExpressPayResult.SUCCESS")
                    print(">> >> $successResult")

                }else if(result.result.result == ExpresspayResult.ACCEPTED){
                    print(">> >> ExpressPayResult.ACCEPTED")
                    print(">> >> $successResult")

                }else if(result.result.result == ExpresspayResult.DECLINED){
                    print(">> >> ExpressPayResult.DECLINED")
                    print(">> >> $successResult")

                }else if(result.result.result == ExpresspayResult.ERROR){
                    print(">> >> ExpressPayResult.ERROR")
                    print(">> >> $successResult")

                }


            }


        }

        override fun onError(error: ExpresspayError){
            print(error.message)
            ExpressCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, error) }
        }

        override fun onFailure(throwable: Throwable) {
            super.onFailure(throwable)
            print(throwable.message)
            ExpressCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, throwable) }

        }
    }
}


internal fun ExpressCardPayFragment.transactionCompleted(data: ExpresspayGetTransactionDetailsSuccess?, error: ExpresspayError?
) {

    requireActivity().finish()

    if(error != null)
        ExpressCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, error) }

    else if(data == null)
        ExpressCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, error) }

    else
        with(data) {
            when (status) {
                ExpresspayStatus.SETTLED -> ExpressCardPay.shared()!!._onTransactionSuccess?.let { success -> success(saleResponse, data) }
                else -> ExpressCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, data) }

//                ExpresspayStatus.SECURE_3D -> TODO()
//                ExpresspayStatus.REDIRECT -> TODO()
//                ExpresspayStatus.PENDING -> TODO()
//                ExpresspayStatus.REVERSAL -> TODO()
//                ExpresspayStatus.REFUND -> TODO()
//                ExpresspayStatus.CHARGEBACK -> TODO()
//                ExpresspayStatus.DECLINED -> TODO()
            }
        }


}