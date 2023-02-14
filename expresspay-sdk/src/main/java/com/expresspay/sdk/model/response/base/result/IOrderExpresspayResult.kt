/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.base.result

/**
 * The base response order result data holder description.
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 * @see IExpresspayResult
 */
interface IOrderExpresspayResult : IExpresspayResult {
    /**
     * Amount of capture.
     */
    val orderAmount: Double

    /**
     * Currency.
     */
    val orderCurrency: String
}
