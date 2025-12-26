package com.edfapg.sdk;

import android.util.Log
import com.edfapg.sdk.core.EdfaPgSdk
import com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResult
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadCallback
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResponse
import com.edfapg.sdk.model.response.gettransactionstatus.EdfaPgSadadSuccess


public class EdfaSadadPay {
    private var orderId: String? = null
    private var orderAmount: Double? = null
    private var orderDescription: String? = null
    private var customerName: String? = null
    private var mobileNumber: String? = null

    private var _onTransactionFailure: ((EdfaPgError?, exception: Throwable) -> Unit)? = null
    private var _onTransactionSuccess: ((EdfaPgSadadSuccess) -> Unit)? = null

    fun setOrderId(id:String): EdfaSadadPay{
        orderId = id
        return this
    }
    fun setOrderAmount(amount:Double): EdfaSadadPay{
        orderAmount = amount
        return this
    }

    fun setOrderDescription(description:String): EdfaSadadPay {
        orderDescription = description
        return this
    }

    fun setCustomerName(name:String): EdfaSadadPay {
        customerName = name
        return this
    }

    fun setMobileNumber(number:String): EdfaSadadPay {
        mobileNumber = number
        return this
    }

    fun onFailure(callback: (EdfaPgError?, error: Throwable) -> Unit): EdfaSadadPay {
        _onTransactionFailure = callback
        return this
    }

    fun onSuccess(callback: (EdfaPgSadadSuccess) -> Unit): EdfaSadadPay {
        _onTransactionSuccess = callback
        return this
    }

    fun initialize(onError: (List<String>) -> Unit) {
        val errors = validate()
        when {
            errors.isEmpty() -> begin()
            else -> onError(errors)
        }

    }

    private fun  begin(){
        EdfaPgSdk.Adapter.SADAD.execute(
            customerName = customerName!!,
            mobileNumber = mobileNumber!!,
            orderAmount = orderAmount!!,
            orderDescription = orderDescription!!,
            orderId = orderId!!,
            callback =  object : EdfaPgSadadCallback{
                override fun onResponse(response: EdfaPgSadadResponse) {
                    super.onResponse(response)
                }

                override fun onResult(result: EdfaPgSadadResult) {
                    when(result){
                        is EdfaPgSadadResult.Success -> {
                            _onTransactionSuccess?.invoke(result.result)
                        }

                        else -> {}
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                    _onTransactionFailure?.invoke(null, throwable)
                }

                override fun onError(error: EdfaPgError) {
                    _onTransactionFailure?.invoke(error, Exception(error.getSafeMessage()))
                }
            }
        )
    }

    private fun validate() = mutableListOf<String>().apply {
        if (orderAmount == null) {
            add("orderAmount is empty")
        }
        if (orderId.isNullOrBlank()) {
            add("orderId is empty")
        }
        if (orderDescription.isNullOrBlank()) {
            add("orderDescription is empty")
        }
        if (customerName.isNullOrBlank()) {
            add("customerName is empty")
        }
        if (mobileNumber.isNullOrBlank()) {
            add("mobileNumber is empty")
        }
    }

}
