/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.gettransactiondetails

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.result.IOrderExpresspayResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The GET_TRANS_DETAILS success result of the [ExpresspayGetTransactionDetailsResult].
 * @see ExpresspayGetTransactionDetailsResponse
 * @see ExpresspayTransaction
 *
 * @property name payer name.
 * @property mail payer mail.
 * @property ip payer IP.
 * @property card payer card number. Format: XXXXXXXX****XXXX.
 * @property transactions the [ExpresspayTransaction] list.
 */
data class ExpresspayGetTransactionDetailsSuccess(
    @NonNull
    @SerializedName("action")
    override val action: ExpresspayAction,
    @NonNull
    @SerializedName("result")
    override val result: ExpresspayResult,
    @Nullable
    @SerializedName("decline_reason")
    val declineReason: String,
    @NonNull
    @SerializedName("status")
    override val status: ExpresspayStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String,
    @NonNull
    @SerializedName("name")
    val name: String,
    @NonNull
    @SerializedName("mail")
    val mail: String,
    @NonNull
    @SerializedName("ip")
    val ip: String,
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,
    @NonNull
    @SerializedName("card")
    val card: String,
    @NonNull
    @SerializedName("transactions")
    val transactions: List<ExpresspayTransaction>
) : IOrderExpresspayResult, Serializable
