/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.gettransactiondetails

import com.edfapg.sdk.feature.adapter.EdfaPgCallback
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.result.IOrderEdfaPgResult

/**
 * The response of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgGetTransactionDetailsResponse =
        EdfaPgResponse<EdfaPgGetTransactionDetailsResult>

/**
 * The callback of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgGetTransactionDetailsCallback =
        EdfaPgCallback<EdfaPgGetTransactionDetailsResult, EdfaPgGetTransactionDetailsResponse>

/**
 * The result of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 *
 * @param result the [IOrderEdfaPgResult].
 */
sealed class EdfaPgGetTransactionDetailsResult(open val result: IOrderEdfaPgResult) {

    /**
     * Success result.
     *
     * @property result the [EdfaPgGetTransactionDetailsSuccess].
     */
    data class Success(override val result: EdfaPgGetTransactionDetailsSuccess) :
        EdfaPgGetTransactionDetailsResult(result)
}
