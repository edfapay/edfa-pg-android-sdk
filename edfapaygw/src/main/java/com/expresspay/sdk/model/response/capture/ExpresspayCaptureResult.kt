/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.capture

import com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.base.result.IDetailsEdfaPgResult
import com.edfapaygw.sdk.model.response.sale.EdfaPgSaleDecline

/**
 * The response of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCaptureAdapter].
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgCaptureResponse = EdfaPgResponse<EdfaPgCaptureResult>

/**
 * The callback of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCaptureAdapter].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgCaptureCallback = EdfaPgCallback<EdfaPgCaptureResult, EdfaPgCaptureResponse>

/**
 * The result of the [com.edfapaygw.sdk.feature.adapter.EdfaPgCaptureAdapter].
 *
 * @param result the [IDetailsEdfaPgResult].
 */
sealed class EdfaPgCaptureResult(open val result: IDetailsEdfaPgResult) {

    /**
     * Success result.
     *
     * @property result the [EdfaPgCaptureSuccess].
     */
    data class Success(override val result: EdfaPgCaptureSuccess) : EdfaPgCaptureResult(result)

    /**
     * Decline result.
     *
     * @property result the [EdfaPgSaleDecline].
     */
    data class Decline(override val result: EdfaPgSaleDecline) : EdfaPgCaptureResult(result)
}
