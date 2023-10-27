/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.creditvoid

import com.edfapg.sdk.feature.adapter.EdfaPgCallback
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult

/**
 * The response of the [com.edfapg.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgCreditvoidResponse = EdfaPgResponse<EdfaPgCreditvoidResult>

/**
 * The callback of the [com.edfapg.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgCreditvoidCallback = EdfaPgCallback<EdfaPgCreditvoidResult, EdfaPgCreditvoidResponse>

/**
 * The result of the [com.edfapg.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 *
 * @param result the [IEdfaPgResult].
 */
sealed class EdfaPgCreditvoidResult(open val result: IEdfaPgResult) {

    /**
     * Success result.
     *
     * @property result the [EdfaPgCreditvoidSuccess].
     */
    data class Success(override val result: EdfaPgCreditvoidSuccess) : EdfaPgCreditvoidResult(result)
}
