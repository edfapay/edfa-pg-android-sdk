/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.gettransactionstatus.ExpresspayGetTransactionStatusResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * GET_TRANS_STATUS Service for the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter].
 * @see ExpresspayGetTransactionStatusResponse
 *
 * Gets order status from Payment Platform.
 */
interface ExpresspayGetTransactionStatusService {

    /**
     * Perform GET_TRANS_STATUS request.
     *
     * @param url the [com.expresspay.sdk.core.ExpresspayCredential.PAYMENT_URL].
     * @param action the [com.expresspay.sdk.model.api.ExpresspayAction.GET_TRANS_STATUS].
     * @param clientKey unique key [com.expresspay.sdk.core.ExpresspayCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     * @return the [Call] by [ExpresspayGetTransactionStatusResponse].
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
        @Size(ExpresspayValidation.Text.UUID)
        @Field("client_key")
        clientKey: String,
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        @Field("trans_id")
        transactionId: String,
        @NonNull
        @Field("hash")
        hash: String,
    ): Call<ExpresspayGetTransactionStatusResponse>
}
