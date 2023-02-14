/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * Status – actual status of transaction in Payment Platform.
 * @see com.expresspay.sdk.model.response.base.result.IExpresspayResult
 *
 * @property status the status value.
 */
enum class ExpresspayStatus(val status: String) {
    /**
     * The transaction awaits 3D-Secure validation.
     */
    @SerializedName("3DS")
    SECURE_3D("3DS"),

    /**
     * The transaction is redirected.
     */
    @SerializedName("REDIRECT")
    REDIRECT("REDIRECT"),

    /**
     * The transaction awaits CAPTURE.
     */
    @SerializedName("PENDING")
    PENDING("PENDING"),

    /**
     * Successful transaction.
     */
    @SerializedName("SETTLED")
    SETTLED("SETTLED"),

    /**
     * Transaction for which reversal was made.
     */
    @SerializedName("REVERSAL")
    REVERSAL("REVERSAL"),

    /**
     * Transaction for which refund was made.
     */
    @SerializedName("REFUND")
    REFUND("REFUND"),

    /**
     * Transaction for which chargeback was made.
     */
    @SerializedName("CHARGEBACK")
    CHARGEBACK("CHARGEBACK"),

    /**
     * Not successful transaction.
     */
    @SerializedName("DECLINED")
    DECLINED("DECLINED"),
}
