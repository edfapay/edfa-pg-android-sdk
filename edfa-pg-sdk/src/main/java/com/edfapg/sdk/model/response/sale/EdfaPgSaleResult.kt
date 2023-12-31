/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.sale

import com.edfapg.sdk.feature.adapter.EdfaPgCallback
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.base.result.IDetailsEdfaPgResult

/**
 * The response of the [com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter].
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 */
typealias EdfaPgSaleResponse = EdfaPgResponse<EdfaPgSaleResult>

/**
 * The callback of the [com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgCallback
 */
typealias EdfaPgSaleCallback = EdfaPgCallback<EdfaPgSaleResult, EdfaPgSaleResponse>

/**
 * The result of the [com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter].
 *
 * @param result the [IDetailsEdfaPgResult].
 */
sealed class EdfaPgSaleResult(open val result: IDetailsEdfaPgResult) {

    /**
     * Success result.
     *
     * @property result the [EdfaPgSaleSuccess].
     */
    data class Success(override val result: EdfaPgSaleSuccess) : EdfaPgSaleResult(result)

    /**
     * Decline result.
     *
     * @property result the [EdfaPgSaleDecline].
     */
    data class Decline(override val result: EdfaPgSaleDecline) : EdfaPgSaleResult(result)

    /**
     * Recurring Init result.
     *
     * @property result the [EdfaPgSaleRecurring].
     */
    data class Recurring(override val result: EdfaPgSaleRecurring) : EdfaPgSaleResult(result)

    /**
     * 3DS result.
     *
     * @property result the [EdfaPgSale3Ds].
     */
    data class Secure3d(override val result: EdfaPgSale3Ds) : EdfaPgSaleResult(result)

    /**
     * Redirect result (for 3DS OTP Required by Bank).
     *
     * @property result the [EdfaPgSale3Ds].
     */
    data class Redirect(override val result: EdfaPgSaleRedirect) : EdfaPgSaleResult(result)
}
