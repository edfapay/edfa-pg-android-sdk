/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.base.error

import androidx.annotation.NonNull
import com.edfapg.sdk.model.api.EdfaPgResult
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Transient
import java.io.Serializable
import java.util.HashMap

/**
 * The error response data holder.
 * Presented as the param in [com.edfapg.sdk.model.response.base.EdfaPgResponse.Error].
 * @see com.edfapg.sdk.model.response.base.EdfaPgResponse
 * @see com.edfapg.sdk.feature.deserializer.EdfaPgBaseDeserializer
 * @see com.edfapg.sdk.feature.adapter.EdfaPgBaseAdapter
 * @see com.edfapg.sdk.feature.adapter.EdfaPgBaseAdapter
 * @see EdfaPgExactError
 *
 * @property result always [EdfaPgResult.ERROR].
 * @property code error code.
 * @property message error message.
 * @property exactErrors list of the [EdfaPgExactError].
 */
data class EdfaPgError(
    @NonNull
    @SerializedName("result")
    val result: EdfaPgResult,
    @NonNull
    @SerializedName("error_code")
    val code: Int,
    @NonNull
    @SerializedName("error_message")
    val message: String?,
    @SerializedName("errors")
    val exactErrors: List<EdfaPgExactError>,

    @Transient
    var raw: Map<*,*>? = null
) : Serializable{

    fun getSafeMessage() = message ?: raw?.get("message") as? String  ?: "STWR"
}

/* Response example:
{
    "result": "ERROR",
    "error_code": 100000,
    "error_message": "Request data is invalid.",
    "errors": [
    {
        "error_code": 100000,
        "error_message": "card_number: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "card_exp_month: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "card_exp_year: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "card_cvv2: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "order_id: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "order_amount: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "order_amount: This value should be greater than 0."
    },
    {
        "error_code": 100000,
        "error_message": "order_currency: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "order_description: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_first_name: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_last_name: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_address: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_country: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_city: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_zip: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_email: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_phone: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "payer_ip: This value should not be blank."
    },
    {
        "error_code": 100000,
        "error_message": "term_url3ds: This value should not be blank."
    }
    ]
}
*/
