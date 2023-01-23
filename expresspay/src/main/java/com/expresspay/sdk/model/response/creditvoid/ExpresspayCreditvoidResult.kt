/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.creditvoid

import com.expresspay.sdk.feature.adapter.ExpresspayCallback
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.base.result.IExpresspayResult

/**
 * The response of the [com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter].
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
typealias ExpresspayCreditvoidResponse = ExpresspayResponse<ExpresspayCreditvoidResult>

/**
 * The callback of the [com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayCallback
 */
typealias ExpresspayCreditvoidCallback = ExpresspayCallback<ExpresspayCreditvoidResult, ExpresspayCreditvoidResponse>

/**
 * The result of the [com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter].
 *
 * @param result the [IExpresspayResult].
 */
sealed class ExpresspayCreditvoidResult(open val result: IExpresspayResult) {

    /**
     * Success result.
     *
     * @property result the [ExpresspayCreditvoidSuccess].
     */
    data class Success(override val result: ExpresspayCreditvoidSuccess) : ExpresspayCreditvoidResult(result)
}
