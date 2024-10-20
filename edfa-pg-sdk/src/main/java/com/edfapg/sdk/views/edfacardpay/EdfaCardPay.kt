package com.edfapg.sdk.views.edfacardpay

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.DesignType

internal var instance: EdfaCardPay? = null

interface EdfapayCardDetailsInitializer {
    fun initialize(
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ){

    }
}


open class EdfaCardPay : EdfapayCardDetailsInitializer{
    constructor() {
        instance = this
    }

    var _order: EdfaPgSaleOrder? = null
    var _payer: EdfaPgPayer? = null
    var _onTransactionFailure: ((EdfaPgSaleResponse?, Any?) -> Unit)? = null
    var _onTransactionSuccess: ((EdfaPgSaleResponse?, Any?) -> Unit)? = null
    var _onError: ((Any) -> Unit)? = null
    var _onPresent: ((Activity) -> Unit)? = null

    fun setOrder(order: EdfaPgSaleOrder): EdfaCardPay {
        _order = order
        return this
    }

    fun setPayer(payer: EdfaPgPayer): EdfaCardPay {
        _payer = payer
        return this
    }

    fun onTransactionFailure(callback: (EdfaPgSaleResponse?, Any?) -> Unit): EdfaCardPay {
        _onTransactionFailure = callback
        return this
    }

    fun onTransactionSuccess(callback: (EdfaPgSaleResponse?, Any?) -> Unit): EdfaCardPay {
        _onTransactionSuccess = callback
        return this
    }

    fun initialize(
        context: Context,
        designType: DesignType? = DesignType.PAYMENT_DESIGN_1,
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ) {
        _onError = onError
        _onPresent = onPresent
        context.startActivity(
            intent(
                context,
                designType ?: DesignType.PAYMENT_DESIGN_1,
                onError,
                onPresent
            )
        )
    }

    fun intent(
        context: Context,
        designType: DesignType,
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ): Intent {
        _onError = onError
        _onPresent = onPresent

        if (designType.value == "0") {
            val intent = Intent(context, EdfaCardPayActivity::class.java)
            return intent
        }else {
        val intent = Intent(context, PaymentActivity::class.java)
        intent.putExtra("paymentDesign", designType.value)
            return intent
        }

    }

    fun fragment(onError: (Any) -> Unit, onPresent: (Activity) -> Unit): Fragment {
        _onError = onError
        _onPresent = onPresent

        return EdfaCardPayFragment()
    }

    companion object {
        fun shared(): EdfaCardPay? {
            return instance
        }
    }
}