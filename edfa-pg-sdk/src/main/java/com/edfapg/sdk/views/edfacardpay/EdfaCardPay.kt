package com.edfapg.sdk.views.edfacardpay

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.options.EdfaPgSaleOptions
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.toolbox.EdfaPayDesignType
import com.edfapg.sdk.toolbox.EdfaPayLanguage
import com.edfapg.sdk.toolbox.delayAtIO

internal var instance: EdfaCardPay? = null

interface EdfapayCardDetailsInitializer {
    fun initialize(
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit
    ) {

    }
}

private var isCalled = false
open class EdfaCardPay : EdfapayCardDetailsInitializer {
    constructor() {
        instance = this
    }

    var _order: EdfaPgSaleOrder? = null
    var _payer: EdfaPgPayer? = null
    var _card: EdfaPgCard? = null
    var _design: EdfaPayDesignType = EdfaPayDesignType.one
    var _language: EdfaPayLanguage = EdfaPayLanguage.en
    var _recurring: EdfaPgSaleOptions? = null
    var _saleAuth = false

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

    fun setRecurring(recurring: Boolean = true): EdfaCardPay {
        _recurring = EdfaPgSaleOptions("", recurring)
        return this
    }

    fun setAuth(auth:Boolean = true): EdfaCardPay {
        _saleAuth = auth
        return this
    }

    fun setDesignType(designType: EdfaPayDesignType): EdfaCardPay {
        _design = designType
        return this
    }

    fun setLanguage(language: EdfaPayLanguage): EdfaCardPay {
        _language = language
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
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit,

    ) {

        if(isCalled){
            return
        }

        isCalled = true
        delayAtIO(500){
            isCalled = false
        }

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
                    onError,
                    onPresent
                )
            )
    }

    fun intent(
        context: Context,
        onError: (Any) -> Unit,
        onPresent: (Activity) -> Unit,
    ): Intent {
        _onError = onError
        _onPresent = onPresent

        val intent = Intent(context, PaymentActivity::class.java)
        intent.putExtra("design", _design.value)
        intent.putExtra("locale", _language.value)
        return intent

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