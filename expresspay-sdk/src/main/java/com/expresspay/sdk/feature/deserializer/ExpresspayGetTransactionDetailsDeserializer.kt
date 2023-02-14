/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResponse
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.expresspay.sdk.feature.adapter.ExpresspayGetTransactionDetailsAdapter].
 * @see ExpresspayGetTransactionDetailsResult
 * @see ExpresspayGetTransactionDetailsResponse
 */
class ExpresspayGetTransactionDetailsDeserializer :
    ExpresspayBaseDeserializer<ExpresspayGetTransactionDetailsResult, ExpresspayGetTransactionDetailsResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<ExpresspayGetTransactionDetailsResult> {
        val getTransactionDetailsResult = ExpresspayGetTransactionDetailsResult.Success(context.parse(jsonObject))
        return ExpresspayResponse.Result(getTransactionDetailsResult, jsonObject)
    }
}
