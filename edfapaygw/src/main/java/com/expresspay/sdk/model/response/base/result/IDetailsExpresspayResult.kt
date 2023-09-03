/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.base.result

import java.util.*

/**
 * The base response details result data holder description.
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 * @see IOrderEdfaPgResult
 */
interface IDetailsEdfaPgResult : IOrderEdfaPgResult {
    /**
     * Transaction date in the Payment Platform.
     */
    val transactionDate: Date

    /**
     * Descriptor from the bank, the same as cardholder will see in the bank statement. Optional.
     */
    val descriptor: String?
}
