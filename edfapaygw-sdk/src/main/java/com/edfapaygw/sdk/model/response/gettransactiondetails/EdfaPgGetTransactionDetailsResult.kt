/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.gettransactiondetails

import com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.base.result.IOrderEdfaPgResult

/**
 * The response of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgGetTransactionDetailsResponse =
        EdfaPgResponse<EdfaPgGetTransactionDetailsResult>

/**
 * The callback of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgGetTransactionDetailsCallback =
        EdfaPgCallback<EdfaPgGetTransactionDetailsResult, EdfaPgGetTransactionDetailsResponse>

/**
 * The result of the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
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
