/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.gettransactiondetails

import com.expresspay.sdk.feature.adapter.ExpresspayCallback
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.base.result.IOrderExpresspayResult

/**
 * The response of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter].
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
typealias ExpresspayGetTransactionDetailsResponse =
        ExpresspayResponse<ExpresspayGetTransactionDetailsResult>

/**
 * The callback of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayCallback
 */
typealias ExpresspayGetTransactionDetailsCallback =
        ExpresspayCallback<ExpresspayGetTransactionDetailsResult, ExpresspayGetTransactionDetailsResponse>

/**
 * The result of the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter].
 *
 * @param result the [IOrderExpresspayResult].
 */
sealed class ExpresspayGetTransactionDetailsResult(open val result: IOrderExpresspayResult) {

    /**
     * Success result.
     *
     * @property result the [ExpresspayGetTransactionDetailsSuccess].
     */
    data class Success(override val result: ExpresspayGetTransactionDetailsSuccess) :
        ExpresspayGetTransactionDetailsResult(result)
}
