/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.deserializer

import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResponse
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapg.sdk.feature.adapter.EdfaPgGetTransactionDetailsAdapter].
 * @see EdfaPgGetTransactionDetailsResult
 * @see EdfaPgGetTransactionDetailsResponse
 */
class EdfaPgGetTransactionDetailsDeserializer :
    EdfaPgBaseDeserializer<EdfaPgGetTransactionDetailsResult, EdfaPgGetTransactionDetailsResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgGetTransactionDetailsResult> {
        val getTransactionDetailsResult = EdfaPgGetTransactionDetailsResult.Success(context.parse(jsonObject))
        return EdfaPgResponse.Result(getTransactionDetailsResult)
    }
}
