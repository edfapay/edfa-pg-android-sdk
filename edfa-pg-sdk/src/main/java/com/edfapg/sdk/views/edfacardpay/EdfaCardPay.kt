package com.edfapg.sdk.views.edfacardpay

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.DesignType
import com.edfapg.sdk.toolbox.EdfaLocale

internal var instance: EdfaCardPay? = null

interface EdfapayCardDetailsInitializer {
    fun initialize(
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ) {

    }
}


open class EdfaCardPay : EdfapayCardDetailsInitializer {
    constructor() {
        instance = this
    }

    var _order: EdfaPgSaleOrder? = null
    var _payer: EdfaPgPayer? = null
    var _card: EdfaPgCard? = null
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

    fun setCard(card: EdfaPgCard): EdfaCardPay {
        _card = card
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
        locale: EdfaLocale? = EdfaLocale.EN,
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ) {
        _onError = onError
        _onPresent = onPresent
        val validationErrors = _payer?.validate()
        if (validationErrors != null) {
            if (validationErrors.isNotEmpty()) {
                _onError?.invoke(
                    validationErrors.toString()
                        .removeSurrounding("[", "]")
                )
                return
            }
        }

        val orderValidationErrors = _order?.validate()
        if (orderValidationErrors != null) {
            if (orderValidationErrors.isNotEmpty()) {
                _onError?.invoke(orderValidationErrors.toString().removeSurrounding("[", "]"))
                return

            }
        }
        context.startActivity(
            intent(
                context,
                designType ?: DesignType.PAYMENT_DESIGN_1,
                locale ?: EdfaLocale.EN,
                onError,
                onPresent
            )
        )
    }

    fun intent(
        context: Context,
        designType: DesignType,
        locale: EdfaLocale,
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ): Intent {
        _onError = onError
        _onPresent = onPresent

        if (designType.value == "0") {
            val intent = Intent(context, EdfaCardPayActivity::class.java)
            return intent
        } else {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("paymentDesign", designType.value)
            intent.putExtra("locale", locale.value)
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