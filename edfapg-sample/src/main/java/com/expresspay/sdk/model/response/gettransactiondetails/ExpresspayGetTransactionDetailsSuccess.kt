/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.gettransactiondetails

import androidx.annotation.NonNull
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.result.IOrderEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The GET_TRANS_DETAILS success result of the [EdfaPgGetTransactionDetailsResult].
 * @see EdfaPgGetTransactionDetailsResponse
 * @see EdfaPgTransaction
 *
 * @property name payer name.
 * @property mail payer mail.
 * @property ip payer IP.
 * @property card payer card number. Format: XXXXXXXX****XXXX.
 * @property transactions the [EdfaPgTransaction] list.
 */
data class EdfaPgGetTransactionDetailsSuccess(
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
    val transactions: List<EdfaPgTransaction>
) : IOrderEdfaPgResult, Serializable
