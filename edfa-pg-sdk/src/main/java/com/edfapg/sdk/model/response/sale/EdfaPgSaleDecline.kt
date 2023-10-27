/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.sale

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.result.IDetailsEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The SALE decline result of the [EdfaPgSaleResult].
 * @see EdfaPgSaleResponse
 *
 * @param declineReason description of the cancellation of the transaction.
 */
data class EdfaPgSaleDecline(
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
    override val transactionId: String,
    @NonNull
    @SerializedName("trans_date")
    override val transactionDate: Date,
    @Nullable
    @SerializedName("descriptor")
    override val descriptor: String,
    @NonNull
    @SerializedName("decline_reason")
    val declineReason: String,
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,
) : IDetailsEdfaPgResult, Serializable
