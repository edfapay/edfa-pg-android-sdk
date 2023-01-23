/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.creditvoid

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.result.IExpresspayResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The CREDITVOID success result of the [ExpresspayCreditvoidResult].
 * @see ExpresspayCreditvoidResponse
 */
data class ExpresspayCreditvoidSuccess(
    @NonNull
    @SerializedName("action")
    override val action: ExpresspayAction,
    @NonNull
    @SerializedName("result")
    override val result: ExpresspayResult,
    @Nullable
    @SerializedName("status")
    override val status: ExpresspayStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String
) : IExpresspayResult, Serializable
