/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import com.edfapaygw.sdk.model.response.creditvoid.EdfaPgCreditvoidResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * CREDITVOID Service for the [com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see EdfaPgCreditvoidResponse
 *
 * CREDITVOID request is used to complete both REFUND and REVERSAL transactions.
 * REVERSAL transaction is used to cancel hold from funds on card account, previously authorized by AUTH transaction.
 * REFUND transaction is used to return funds to card account, previously submitted by SALE or CAPTURE transactions.
 */
interface EdfaPgCreditvoidService {

    /**
     * Perform CREDITVOID request.
     *
     * @param url the [com.edfapaygw.sdk.core.EdfaPgCredential.PAYMENT_URL].
     * @param action the [com.edfapaygw.sdk.model.api.EdfaPgAction.CREDITVOID].
     * @param clientKey unique key [com.edfapaygw.sdk.core.EdfaPgCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.edfapaygw.sdk.toolbox.EdfaPgHashUtil
     * @return the [Call] by [EdfaPgCreditvoidResponse].
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
        @Size(EdfaPgValidation.Text.UUID)
        @Field("client_key")
        clientKey: String,
        @NonNull
        @Size(EdfaPgValidation.Text.UUID)
        @Field("trans_id")
        transactionId: String,
        @Nullable
        @Field("amount")
        amount: String?,
        @NonNull
        @Field("hash")
        hash: String,
    ): Call<EdfaPgCreditvoidResponse>
}
