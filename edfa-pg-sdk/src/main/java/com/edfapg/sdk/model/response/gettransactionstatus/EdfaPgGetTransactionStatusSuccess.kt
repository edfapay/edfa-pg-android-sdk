/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.gettransactionstatus

import androidx.annotation.NonNull
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The GET_TRANS_STATUS success result of the [EdfaPgGetTransactionStatusResult].
 * @see EdfaPgGetTransactionStatusResponse
 */
data class EdfaPgGetTransactionStatusSuccess(
    @NonNull
    @SerializedName("action")
    override val action: EdfaPgAction,
    @NonNull
    @SerializedName("result")
    override val result: EdfaPgResult,
    @NonNull
    @SerializedName("status")
    override val status: EdfaPgStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String
) : IEdfaPgResult, Serializable
