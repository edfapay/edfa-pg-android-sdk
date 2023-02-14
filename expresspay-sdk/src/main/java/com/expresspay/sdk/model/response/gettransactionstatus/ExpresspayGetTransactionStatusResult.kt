/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.gettransactionstatus

import com.expresspay.sdk.feature.adapter.ExpresspayCallback
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.base.result.IExpresspayResult

/**
 * The response of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter].
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
typealias ExpresspayGetTransactionStatusResponse =
        ExpresspayResponse<ExpresspayGetTransactionStatusResult>

/**
 * The callback of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayCallback
 */
typealias ExpresspayGetTransactionStatusCallback =
        ExpresspayCallback<ExpresspayGetTransactionStatusResult, ExpresspayGetTransactionStatusResponse>

/**
 * The result of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter].
 *
 * @param result the [IExpresspayResult].
 */
sealed class ExpresspayGetTransactionStatusResult(open val result: IExpresspayResult) {

    /**
     * Success result.
     *
     * @property result the [ExpresspayGetTransactionStatusSuccess].
     */
    data class Success(override val result: ExpresspayGetTransactionStatusSuccess) :
        ExpresspayGetTransactionStatusResult(result)
}
