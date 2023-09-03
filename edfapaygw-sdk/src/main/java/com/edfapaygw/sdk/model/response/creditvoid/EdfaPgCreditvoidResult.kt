/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.creditvoid

import com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.base.result.IEdfaPgResult

/**
 * The response of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgCreditvoidResponse = EdfaPgResponse<EdfaPgCreditvoidResult>

/**
 * The callback of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgCreditvoidCallback = EdfaPgCallback<EdfaPgCreditvoidResult, EdfaPgCreditvoidResponse>

/**
 * The result of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
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
