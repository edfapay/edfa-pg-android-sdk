/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.sale

import com.expresspay.sdk.feature.adapter.ExpresspayCallback
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.base.result.IDetailsExpresspayResult

/**
 * The response of the [com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter].
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
typealias ExpresspaySaleResponse = ExpresspayResponse<ExpresspaySaleResult>

/**
 * The callback of the [com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayCallback
 */
typealias ExpresspaySaleCallback = ExpresspayCallback<ExpresspaySaleResult, ExpresspaySaleResponse>

/**
 * The result of the [com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter].
 *
 * @param result the [IDetailsExpresspayResult].
 */
sealed class ExpresspaySaleResult(open val result: IDetailsExpresspayResult) {

    /**
     * Success result.
     *
     * @property result the [ExpresspaySaleSuccess].
     */
    data class Success(override val result: ExpresspaySaleSuccess) : ExpresspaySaleResult(result)

    /**
     * Decline result.
     *
     * @property result the [ExpresspaySaleDecline].
     */
    data class Decline(override val result: ExpresspaySaleDecline) : ExpresspaySaleResult(result)

    /**
     * Recurring Init result.
     *
     * @property result the [ExpresspaySaleRecurring].
     */
    data class Recurring(override val result: ExpresspaySaleRecurring) : ExpresspaySaleResult(result)

    /**
     * 3DS result.
     *
     * @property result the [ExpresspaySale3Ds].
     */
    data class Secure3d(override val result: ExpresspaySale3Ds) : ExpresspaySaleResult(result)
}
