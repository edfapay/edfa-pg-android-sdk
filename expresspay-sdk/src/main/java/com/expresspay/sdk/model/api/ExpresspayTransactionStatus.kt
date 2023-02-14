/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * The transaction status types.
 * @see com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayTransaction
 *
 * @property transactionStatus the transaction status value.
 */
enum class ExpresspayTransactionStatus(val transactionStatus: String) {
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
