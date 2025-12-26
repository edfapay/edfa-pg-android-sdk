/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.generatesadadnumber

import com.edfapg.sdk.feature.adapter.EdfaPgCallback
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult
import com.edfapg.sdk.model.response.gettransactionstatus.EdfaPgSadadSuccess

/**
 * The response of the [com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter].
 * @see EdfaPgResponse
 */
typealias EdfaPgSadadResponse =
        EdfaPgResponse<EdfaPgSadadResult>

/**
 * The callback of the [com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter].
 * @see EdfaPgCallback
 */
typealias EdfaPgSadadCallback =
        EdfaPgCallback<EdfaPgSadadResult, EdfaPgSadadResponse>

/**
 * The result of the [com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter].
 *
 * @param result the [IEdfaPgResult].
 */
sealed class EdfaPgSadadResult(open val result: EdfaPgSadadSuccess) {

    /**
     * Success result.
     *
     * @property result the [com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadSuccess].
     */
    data class Success(override val result: EdfaPgSadadSuccess) : EdfaPgSadadResult(result)
}
