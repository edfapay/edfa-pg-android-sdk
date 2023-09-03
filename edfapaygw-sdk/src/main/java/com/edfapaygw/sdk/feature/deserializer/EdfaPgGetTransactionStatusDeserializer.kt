/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.deserializer

import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.gettransactionstatus.EdfaPgGetTransactionStatusResponse
import com.edfapaygw.sdk.model.response.gettransactionstatus.EdfaPgGetTransactionStatusResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapaygw.sdk.feature.adapter.EdfaPgGetTransactionStatusAdapter].
 * @see EdfaPgGetTransactionStatusResult
 * @see EdfaPgGetTransactionStatusResponse
 */
class EdfaPgGetTransactionStatusDeserializer :
    EdfaPgBaseDeserializer<EdfaPgGetTransactionStatusResult, EdfaPgGetTransactionStatusResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgGetTransactionStatusResult> {
        val getTransactionStatusResult = EdfaPgGetTransactionStatusResult.Success(context.parse(jsonObject))
        return EdfaPgResponse.Result(getTransactionStatusResult, jsonObject)
    }
}
