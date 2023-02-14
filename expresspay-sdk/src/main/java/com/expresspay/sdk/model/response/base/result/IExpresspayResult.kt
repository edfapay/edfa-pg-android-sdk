/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.base.result

import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus

/**
 * The base response result data holder description.
 * @see com.expresspay.sdk.model.response.base.ExpresspayResponse
 */
interface IExpresspayResult{
    /**
     * The action of the transaction.
     */
    val action: ExpresspayAction

    /**
     * The result of the transaction.
     */
    val result: ExpresspayResult

    /**
     * The status of the transaction.
     */
    val status: ExpresspayStatus

    /**
     * Transaction ID in the Merchant’s system.
     */
    val orderId: String

    /**
     * Transaction ID in the Payment Platform.
     */
    val transactionId: String
}
