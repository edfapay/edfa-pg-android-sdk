/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.sale

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.edfapaygw.sdk.model.api.EdfaPgAction
import com.edfapaygw.sdk.model.api.EdfaPgResult
import com.edfapaygw.sdk.model.api.EdfaPgStatus
import com.edfapaygw.sdk.model.response.base.result.IDetailsEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The SALE recurring init result of the [EdfaPgSaleResult].
 * @see EdfaPgSaleResponse
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgRecurringSaleAdapter
 *
 * @param recurringToken recurring token (get if account support recurring sales and was initialization transaction
 * for following recurring)
 */
data class EdfaPgSaleRecurring(
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
    override val descriptor: String?,
    @NonNull
    @SerializedName("recurring_token")
    val recurringToken: String,
    @NonNull
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,
) : IDetailsEdfaPgResult, Serializable
