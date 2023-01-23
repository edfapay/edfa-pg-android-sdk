/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * The method of transferring parameters (POST/GET).
 * @see com.expresspay.sdk.model.response.sale.ExpresspaySale3Ds
 *
 * @property redirectMethod the redirect method value.
 */
enum class ExpresspayRedirectMethod(val redirectMethod: String) {
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
