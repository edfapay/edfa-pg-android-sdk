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
 * The SALE Redirect result of the [EdfaPgSaleResult].
 * @see EdfaPgSaleResponse
 */

data class EdfaPgSaleRedirect(
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
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,

    // Upgraded Properties For 3DS REDIRECT
    @SerializedName("redirect_url")
    val redirectUrl: String,
    @SerializedName("redirect_method")
    val redirectMethod: String,
    @SerializedName("redirect_params")
    val redirectParams: EdfaPgRedirectParams,

    ) : IDetailsEdfaPgResult, Serializable


/**
 * The 3DS SALE Redirect data holder. These values are required to proceed with the 3DSecure Payment flow.
 * @see EdfaPgSale3Ds
 *
 * @property body the body message from the DIBS server response.
 */

data class EdfaPgRedirectParams(
    @NonNull
    @SerializedName("body")
    val body: String
) : Serializable


/* *
----------------------------------------------
Sale 3DS REDIRECT for Bank OTP Verification
----------------------------------------------
{
    "action": "SALE",
    "result": "REDIRECT",
    "status": "REDIRECT",
    "order_id": "1F817BE1-8B12-4C0E-B9A8-827818BF6EA0",
    "trans_id": "83e52eee-a6f2-11ed-8bd2-267558dbbf92",
    "trans_date": "2023-02-07 14:20:04",
    "amount": "1.00",
    "currency": "SAR",
    "redirect_url": "https://pay.edfapay.com/collector/83e52eee-a6f2-11ed-8bd2-267558dbbf92",
    "redirect_params": {
        "body": "LzVIVlRqNDYrM3NGTmpxZzVXWlJGNW1CWFBhYVFMNWg2MW5KNTgyVHVOd0JjV2FBNk01RzZMdk9WQ3hid0l1eU5iRnljT0tOV2l4VGRXaUsybFhFZUJoSm5Idy83OUFjWEh6UkNFNG1lZUpoMndSZEpaei8wdUxnSEZKSktmelJGc3VZc0trdzRFVVN5OUFKcTlORVNBPT06OowgxpNgykzRruvghQJpIfc="
    },
    "redirect_method": "POST"
}
* */
