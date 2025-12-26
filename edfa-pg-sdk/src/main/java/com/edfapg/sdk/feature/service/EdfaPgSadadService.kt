/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.service

import androidx.annotation.NonNull
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * GET_TRANS_STATUS Service for the [com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter].
 * @see EdfaPgSadadResponse
 *
 * Gets order status from Payment Platform.
 */
interface EdfaPgSadadService {

    /**
     * Perform GET_TRANS_STATUS request.
     *
     * @param merchantKey the [com.edfapg.sdk.core.EdfaPgCredential.PAYMENT_URL].
     * @param orderId the [com.edfapg.sdk.model.api.EdfaPgAction.GET_TRANS_STATUS].
     * @param clientKey unique key [com.edfapg.sdk.core.EdfaPgCredential.CLIENT_KEY]. UUID format value.
     * @param orderDescription transaction ID in the Payment Platform. UUID format value.
     * @param customerName special signature to validate your request to Payment Platform.
     * @see com.edfapg.sdk.toolbox.EdfaPgHashUtil
     * @return the [Call] by [EdfaPgSadadResponse].
     */
    @POST
    fun generateSadadNumber(
        @NonNull @Url url: String,
        @NonNull @Header("X-Edfapay-Merchant-Key") merchantKey: String,
        @NonNull @Header("X-Signature") hash: String,
        @NonNull @Body() body: @JvmSuppressWildcards Map<String, Any>,
    ): Call<EdfaPgSadadResponse>
}
