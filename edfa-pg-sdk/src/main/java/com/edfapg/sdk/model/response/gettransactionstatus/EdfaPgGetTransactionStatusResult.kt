/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.gettransactionstatus

import com.edfapg.sdk.feature.adapter.EdfaPgCallback
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult

/**
 * The response of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgGetTransactionStatusResponse =
        EdfaPgResponse<EdfaPgGetTransactionStatusResult>

/**
 * The callback of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgGetTransactionStatusCallback =
        EdfaPgCallback<EdfaPgGetTransactionStatusResult, EdfaPgGetTransactionStatusResponse>

/**
 * The result of the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 *
 * @param result the [IEdfaPgResult].
 */
sealed class EdfaPgGetTransactionStatusResult(open val result: IEdfaPgResult) {

    /**
     * Success result.
     *
     * @property result the [EdfaPgGetTransactionStatusSuccess].
     */
    data class Success(override val result: EdfaPgGetTransactionStatusSuccess) :
        EdfaPgGetTransactionStatusResult(result)
}
