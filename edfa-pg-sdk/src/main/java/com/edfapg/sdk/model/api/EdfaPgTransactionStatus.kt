/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * The transaction status types.
 * @see com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgTransaction
 *
 * @property transactionStatus the transaction status value.
 */
enum class EdfaPgTransactionStatus(val transactionStatus: String) {
    /**
     * Failed or "0" status.
     */
    @SerializedName("fail")
    FAIL("fail"),

    /**
     * Success or "1" status.
     */
    @SerializedName("success")
    SUCCESS("success"),
}
