/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation
import com.edfapg.sdk.model.response.gettransactionstatus.EdfaPgGetTransactionStatusResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * GET_TRANS_STATUS Service for the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see EdfaPgGetTransactionStatusResponse
 *
 * Gets order status from Payment Platform.
 */
interface EdfaPgGetTransactionStatusService {

    /**
     * Perform GET_TRANS_STATUS request.
     *
     * @param url the [com.edfapg.sdk.core.EdfaPgCredential.PAYMENT_URL].
     * @param action the [com.edfapg.sdk.model.api.EdfaPgAction.GET_TRANS_STATUS].
     * @param clientKey unique key [com.edfapg.sdk.core.EdfaPgCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.edfapg.sdk.toolbox.EdfaPgHashUtil
     * @return the [Call] by [EdfaPgGetTransactionStatusResponse].
     */
    @FormUrlEncoded
    @POST
    fun getTransactionStatus(
        @NonNull
        @Url
        url: String,
        @NonNull
        @Field("action")
        action: String,
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        @Field("client_key")
        clientKey: String,
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        @Field("trans_id")
        transactionId: String,
        @NonNull
        @Field("hash")
        hash: String,
    ): Call<EdfaPgGetTransactionStatusResponse>
}
