/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.gettransactionstatus.ExpresspayGetTransactionStatusResponse
import com.expresspay.sdk.model.response.gettransactionstatus.ExpresspayGetTransactionStatusResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionStatusAdapter].
 * @see ExpresspayGetTransactionStatusResult
 * @see ExpresspayGetTransactionStatusResponse
 */
class ExpresspayGetTransactionStatusDeserializer :
    ExpresspayBaseDeserializer<ExpresspayGetTransactionStatusResult, ExpresspayGetTransactionStatusResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<ExpresspayGetTransactionStatusResult> {
        val getTransactionStatusResult = ExpresspayGetTransactionStatusResult.Success(context.parse(jsonObject))
        return ExpresspayResponse.Result(getTransactionStatusResult)
    }
}
