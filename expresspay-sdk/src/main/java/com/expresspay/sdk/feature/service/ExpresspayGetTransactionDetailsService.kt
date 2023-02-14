/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Size
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResponse
import com.expresspay.sdk.toolbox.ExpresspayValidation
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * GET_TRANS_DETAILS Service for the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter].
 * @see ExpresspayGetTransactionDetailsResponse
 *
 * Gets all history of transactions by the order.
 */
interface ExpresspayGetTransactionDetailsService {

    /**
     * Perform GET_TRANS_DETAILS request.
     *
     * @param url the [com.expresspay.sdk.core.ExpresspayCredential.PAYMENT_URL].
     * @param action the [com.expresspay.sdk.model.api.ExpresspayAction.GET_TRANS_DETAILS].
     * @param clientKey unique key [com.expresspay.sdk.core.ExpresspayCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     * @return the [Call] by [ExpresspayGetTransactionDetailsResponse].
     */
    @FormUrlEncoded
    @POST
    fun getTransactionDetails(
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
    ): Call<ExpresspayGetTransactionDetailsResponse>
}
