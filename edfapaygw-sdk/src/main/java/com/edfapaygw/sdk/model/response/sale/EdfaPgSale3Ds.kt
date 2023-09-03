/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.response.sale

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.edfapaygw.sdk.model.api.EdfaPgAction
import com.edfapaygw.sdk.model.api.EdfaPgRedirectMethod
import com.edfapaygw.sdk.model.api.EdfaPgResult
import com.edfapaygw.sdk.model.api.EdfaPgStatus
import com.edfapaygw.sdk.model.response.base.result.IDetailsEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The SALE 3DS result of the [EdfaPgSaleResult].
 * @see EdfaPgSaleResponse
 *
 * @property redirectUrl URL to which the Merchant should redirect the Customer.
 * @property redirectParams the [EdfaPgSale3dsRedirectParams].
 * @property redirectMethod the method of transferring parameters (POST/GET).
 */
data class EdfaPgSale3Ds(
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
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,
    @NonNull
    @SerializedName("redirect_url")
    val redirectUrl: String,
    @NonNull
    @SerializedName("redirect_params")
    val redirectParams: EdfaPgSale3dsRedirectParams,
    @NonNull
    @SerializedName("redirect_method")
    val redirectMethod: EdfaPgRedirectMethod,
) : IDetailsEdfaPgResult, Serializable
