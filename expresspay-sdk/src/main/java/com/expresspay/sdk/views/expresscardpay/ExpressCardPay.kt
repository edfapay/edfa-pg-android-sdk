package com.expresspay.sdk.views.expresscardpay

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.expresspay.sdk.model.request.order.ExpresspaySaleOrder
import com.expresspay.sdk.model.request.payer.ExpresspayPayer
import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsSuccess
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResult

internal var instance:ExpressCardPay? = null
class ExpressCardPay {
    constructor(){
        instance = this
    }

    var _order:ExpresspaySaleOrder? = null
    var _payer:ExpresspayPayer? = null
    var _onTransactionFailure:((ExpresspaySaleResponse?, Any?) -> Unit)? = null
    var _onTransactionSuccess:((ExpresspaySaleResponse?, Any?) -> Unit)? = null
    var _onError:((Any) -> Unit)? = null
    var _onPresent:((Activity) -> Unit)? = null

    fun setOrder(order:ExpresspaySaleOrder) : ExpressCardPay{
        _order = order
        return this
    }

    fun setPayer(payer:ExpresspayPayer) : ExpressCardPay{
        _payer = payer
        return this
    }

    fun onTransactionFailure(callback:(ExpresspaySaleResponse?, Any?) -> Unit) : ExpressCardPay{
        _onTransactionFailure = callback
        return this
    }

    fun onTransactionSuccess(callback:(ExpresspaySaleResponse?, Any?) -> Unit) : ExpressCardPay{
        _onTransactionSuccess = callback
        return this
    }

    fun initialize(context:Activity, onError:(Any) -> Unit, onPresent:(Activity) -> Unit){
        _onError = onError
        _onPresent = onPresent

        context.startActivity(intent(context, onError, onPresent))
    }

    fun intent(context:Activity, onError:(Any) -> Unit, onPresent:(Activity) -> Unit) : Intent {
        _onError = onError
        _onPresent = onPresent

        val intent = Intent(context, ExpressCardPayActivity::class.java)
        return  intent
    }

    fun fragment(onError:(Any) -> Unit, onPresent:(Activity) -> Unit) : Fragment {
        _onError = onError
        _onPresent = onPresent

        return  ExpressCardPayFragment()
    }

    companion object{
        internal fun shared() : ExpressCardPay?{
            return instance
        }
    }
}