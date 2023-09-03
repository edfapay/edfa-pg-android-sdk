/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * The method of transferring parameters (POST/GET).
 * @see com.edfapaygw.sdk.model.response.sale.EdfaPgSale3Ds
 *
 * @property redirectMethod the redirect method value.
 */
enum class EdfaPgRedirectMethod(val redirectMethod: String) {
    /**
     * GET redirect method value.
     */
    @SerializedName("GET")
    GET("GET"),

    /**
     * POST redirect method value.
     */
    @SerializedName("POST")
    POST("POST"),
}
