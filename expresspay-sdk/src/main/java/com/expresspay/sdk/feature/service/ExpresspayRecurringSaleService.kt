/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.service

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Size
import com.expresspay.sdk.toolbox.ExpresspayValidation
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * RECURRING_SALE Service for the [com.expresspay.sdk.feature.adapter.ExpresspayRecurringSaleAdapter].
 * @see ExpresspaySaleResponse
 *
 * Recurring payments are commonly used to create new transactions based on already stored cardholder information from
 * previous operations. RECURRING_SALE request has same logic as SALE request, the only difference is that you need to
 * provide primary transaction id, and this request will create a secondary transaction with previously used cardholder
 * data from primary transaction.
 */
interface ExpresspayRecurringSaleService {

    /**
     * Perform RECURRING_SALE request.
     *
     * @param url the [com.expresspay.sdk.core.ExpresspayCredential.PAYMENT_URL].
     * @param action the [com.expresspay.sdk.model.api.ExpresspayAction.RECURRING_SALE].
     * @param clientKey unique key [com.expresspay.sdk.core.ExpresspayCredential.CLIENT_KEY]. UUID format value.
     * @param orderId transaction ID in the Merchants system. String up to 255 characters.
     * @param orderAmount the amount of the transaction. Numbers in the form XXXX.XX (without leading zeros).
     * @param orderDescription description of the transaction (product name). String up to 1024 characters.
     * @param recurringFirstTransactionId transaction ID of the primary transaction in the Payment Platform.
     * UUID format value.
     * @param recurringToken value obtained during the primary transaction. UUID format value.
     * @param auth indicates that transaction must be only authenticated, but not captured. Y or N (default N).
     * @param hash special signature to validate your request to Payment Platform.
     * @see com.expresspay.sdk.toolbox.ExpresspayHashUtil
     * @return the [Call] by [ExpresspaySaleResponse].
     */
    @FormUrlEncoded
    @POST
    fun recurringSale(
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
        @Size(max = ExpresspayValidation.Text.REGULAR)
        @Field("order_id")
        orderId: String,
        @NonNull
        @Field("order_amount")
        orderAmount: String,
        @NonNull
        @Size(max = ExpresspayValidation.Text.LONG)
        @Field("order_description")
        orderDescription: String,
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        @Field("recurring_first_trans_id")
        recurringFirstTransactionId: String,
        @NonNull
        @Size(ExpresspayValidation.Text.UUID)
        @Field("recurring_token")
        recurringToken: String,
        @Nullable
        @Field("auth")
        auth: String?,
        @NonNull
        @Field("hash")
        hash: String,
    ): Call<ExpresspaySaleResponse>
}
