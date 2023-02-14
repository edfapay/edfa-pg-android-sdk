/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.base.result

import java.util.*

/**
 * The base response details result data holder description.
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 * @see IOrderExpresspayResult
 */
interface IDetailsExpresspayResult : IOrderExpresspayResult {
    /**
     * Transaction date in the Payment Platform.
     */
    val transactionDate: Date

    /**
     * Descriptor from the bank, the same as cardholder will see in the bank statement. Optional.
     */
    val descriptor: String?
}
