/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.capture

import com.expresspay.sdk.feature.adapter.ExpresspayCallback
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.base.result.IDetailsExpresspayResult
import com.expresspay.sdk.model.response.sale.ExpresspaySaleDecline

/**
 * The response of the [com.expresspay.sdk.feature.adapter.ExpresspayCaptureAdapter].
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
typealias ExpresspayCaptureResponse = ExpresspayResponse<ExpresspayCaptureResult>

/**
 * The callback of the [com.expresspay.sdk.feature.adapter.ExpresspayCaptureAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayCallback
 */
typealias ExpresspayCaptureCallback = ExpresspayCallback<ExpresspayCaptureResult, ExpresspayCaptureResponse>

/**
 * The result of the [com.expresspay.sdk.feature.adapter.ExpresspayCaptureAdapter].
 *
 * @param result the [IDetailsExpresspayResult].
 */
sealed class ExpresspayCaptureResult(open val result: IDetailsExpresspayResult) {

    /**
     * Success result.
     *
     * @property result the [ExpresspayCaptureSuccess].
     */
    data class Success(override val result: ExpresspayCaptureSuccess) : ExpresspayCaptureResult(result)

    /**
     * Decline result.
     *
     * @property result the [ExpresspaySaleDecline].
     */
    data class Decline(override val result: ExpresspaySaleDecline) : ExpresspayCaptureResult(result)
}
