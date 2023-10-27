/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.creditvoid

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The CREDITVOID success result of the [EdfaPgCreditvoidResult].
 * @see EdfaPgCreditvoidResponse
 */
data class EdfaPgCreditvoidSuccess(
    @NonNull
    @SerializedName("action")
    override val action: EdfaPgAction,
    @NonNull
    @SerializedName("result")
    override val result: EdfaPgResult,
    @Nullable
    @SerializedName("status")
    override val status: EdfaPgStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String
) : IEdfaPgResult, Serializable
