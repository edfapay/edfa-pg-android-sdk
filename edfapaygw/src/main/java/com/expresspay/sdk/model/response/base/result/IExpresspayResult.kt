/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.base.result

import com.edfapaygw.sdk.model.api.EdfaPgAction
import com.edfapaygw.sdk.model.api.EdfaPgResult
import com.edfapaygw.sdk.model.api.EdfaPgStatus

/**
 * The base response result data holder description.
 * @see com.edfapaygw.sdk.model.response.base.EdfaPgResponse
 */
interface IEdfaPgResult {
    /**
     * The action of the transaction.
     */
    val action: EdfaPgAction

    /**
     * The result of the transaction.
     */
    val result: EdfaPgResult

    /**
     * The status of the transaction.
     */
    val status: EdfaPgStatus

    /**
     * Transaction ID in the Merchant’s system.
     */
    val orderId: String

    /**
     * Transaction ID in the Payment Platform.
     */
    val transactionId: String
}
