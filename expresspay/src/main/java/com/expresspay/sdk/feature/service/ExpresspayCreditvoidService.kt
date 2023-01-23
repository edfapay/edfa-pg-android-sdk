/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.creditvoid.ExpresspayCreditvoidResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * CREDITVOID Service for the [com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter].
 * @see ExpresspayCreditvoidResponse
 *
 * CREDITVOID request is used to complete both REFUND and REVERSAL transactions.
 * REVERSAL transaction is used to cancel hold from funds on card account, previously authorized by AUTH transaction.
 * REFUND transaction is used to return funds to card account, previously submitted by SALE or CAPTURE transactions.
 */
interface ExpresspayCreditvoidService {

    /**
     * Perform CREDITVOID request.
     *
     * @param url the [com.expresspay.sdk.core.ExpresspayCredential.PAYMENT_URL].
     * @param action the [com.expresspay.sdk.model.api.ExpresspayAction.CREDITVOID].
     * @param clientKey unique key [com.expresspay.sdk.core.ExpresspayCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     * @return the [Call] by [ExpresspayCreditvoidResponse].
     */
    @FormUrlEncoded
    @POST
    fun creditvoid(
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
        @Nullable
        @Field("amount")
        amount: String?,
        @NonNull
        @Field("hash")
        hash: String,
    ): Call<ExpresspayCreditvoidResponse>
}
