/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.gettransactiondetails

import androidx.annotation.FloatRange
import androidx.annotation.NonNull
import com.edfapaygw.sdk.model.api.EdfaPgTransactionStatus
import com.edfapaygw.sdk.model.api.EdfaPgTransactionType
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The transaction data holder.
 * @see EdfaPgGetTransactionDetailsSuccess
 *
 * @property date transaction date.
 * @property type transaction type (sale, 3ds, auth, capture, chargeback, reversal, refund).
 * @property status transaction status (0-fail, 1-success).
 * @property amount transaction amount.
 */
data class EdfaPgTransaction(
    @NonNull
    @SerializedName("date")
    val date: Date,
    @NonNull
    @SerializedName("type")
    val type: EdfaPgTransactionType,
    @NonNull
    @SerializedName("status")
    val status: EdfaPgTransactionStatus,
    @NonNull
    @SerializedName("amount")
    @FloatRange(from = EdfaPgValidation.Amount.VALUE_MIN)
    val amount: Double,
) : Serializable

/* Response example:
{
    "transactions": [
    {
        "date": "2012-01-01 01:10:25",
        "type": "AUTH",
        "status": "1",
        "amount": "1.95"
    },
    {
        "date": "2012-01-01 01:11:30",
        "type": "CAPTURE",
        "status": "1",
        "amount": "1.95"
    },
    {
        "date": "2012-02-06 10:25:06",
        "type": "REFUND",
        "status": "1",
        "amount": "1.95"
    }
    ]
}
*/
