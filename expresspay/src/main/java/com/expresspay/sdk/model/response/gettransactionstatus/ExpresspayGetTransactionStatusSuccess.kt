/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.gettransactionstatus

import androidx.annotation.NonNull
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.result.IExpresspayResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The GET_TRANS_STATUS success result of the [ExpresspayGetTransactionStatusResult].
 * @see ExpresspayGetTransactionStatusResponse
 */
data class ExpresspayGetTransactionStatusSuccess(
    @NonNull
    @SerializedName("action")
    override val action: ExpresspayAction,
    @NonNull
    @SerializedName("result")
    override val result: ExpresspayResult,
    @NonNull
    @SerializedName("status")
    override val status: ExpresspayStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String
) : IExpresspayResult, Serializable
