/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.base.result

/**
 * The base response order result data holder description.
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 * @see IEdfaPgResult
 */
interface IOrderEdfaPgResult : IEdfaPgResult {
    /**
     * Amount of capture.
     */
    val orderAmount: Double

    /**
     * Currency.
     */
    val orderCurrency: String
}
