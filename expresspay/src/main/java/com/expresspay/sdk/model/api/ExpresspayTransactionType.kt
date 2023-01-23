/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * The transaction type types.
 * @see com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayTransaction
 *
 * @property transactionType the transaction type value.
 */
enum class ExpresspayTransactionType(val transactionType: String) {
    /**
     * 3DS transaction type.
     */
    @SerializedName("3DS")
    SECURE_3D("3DS"),

    /**
     * SALE transaction type.
     */
    @SerializedName("SALE")
    SALE("SALE"),

    /**
     * AUTH transaction type.
     */
    @SerializedName("AUTH")
    AUTH("AUTH"),

    /**
     * CAPTURE transaction type.
     */
    @SerializedName("CAPTURE")
    CAPTURE("CAPTURE"),

    /**
     * REVERSAL transaction type.
     */
    @SerializedName("REVERSAL")
    REVERSAL("REVERSAL"),

    /**
     * REFUND transaction type.
     */
    @SerializedName("REFUND")
    REFUND("REFUND"),

    /**
     * CHARGEBACK transaction type.
     */
    @SerializedName("CHARGEBACK")
    CHARGEBACK("CHARGEBACK"),
}
