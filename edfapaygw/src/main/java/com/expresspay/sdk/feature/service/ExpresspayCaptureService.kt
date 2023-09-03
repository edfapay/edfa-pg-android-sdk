/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.edfapaygw.sdk.toolbox.EdfaPgValidation
import com.edfapaygw.sdk.model.response.capture.EdfaPgCaptureResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * CAPTURE Service for the [com.edfapaygw.sdk.feature.adapter.EdfaPgCaptureAdapter].
 * @see EdfaPgCaptureResponse
 *
 * CAPTURE request is used to submit previously authorized transaction (created by SALE request with
 * parameter auth = Y). Hold funds will be transferred to Merchants account.
 */
interface EdfaPgCaptureService {

    /**
     * Perform CAPTURE request.
     *
     * @param url the [com.edfapaygw.sdk.core.EdfaPgCredential.PAYMENT_URL].
     * @param action the [com.edfapaygw.sdk.model.api.EdfaPgAction.CAPTURE].
     * @param clientKey unique key [com.edfapaygw.sdk.core.EdfaPgCredential.CLIENT_KEY]. UUID format value.
     * @param transactionId transaction ID in the Payment Platform. UUID format value.
     * @param amount the amount for capture. Only one partial capture is allowed.
     * Numbers in the form XXXX.XX (without leading zeros).
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.edfapaygw.sdk.toolbox.EdfaPgHashUtil
     * @return the [Call] by [EdfaPgCaptureResponse].
     */
    @FormUrlEncoded
    @POST
    fun capture(
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
    ): Call<EdfaPgCaptureResponse>
}
