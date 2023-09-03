/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.gettransactionstatus

import com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.base.result.IEdfaPgResult

/**
 * The response of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgGetTransactionStatusResponse =
        EdfaPgResponse<EdfaPgGetTransactionStatusResult>

/**
 * The callback of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgGetTransactionStatusCallback =
        EdfaPgCallback<EdfaPgGetTransactionStatusResult, EdfaPgGetTransactionStatusResponse>

/**
 * The result of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
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
