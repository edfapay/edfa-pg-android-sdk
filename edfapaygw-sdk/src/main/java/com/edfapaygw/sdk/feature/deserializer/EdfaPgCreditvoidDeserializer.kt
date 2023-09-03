/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.deserializer

import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.creditvoid.EdfaPgCreditvoidResponse
import com.edfapaygw.sdk.model.response.creditvoid.EdfaPgCreditvoidResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapaygw.sdk.feature.adapter.EdfaPgCreditvoidAdapter].
 * @see EdfaPgCreditvoidResult
 * @see EdfaPgCreditvoidResponse
 */
class EdfaPgCreditvoidDeserializer :
    EdfaPgBaseDeserializer<EdfaPgCreditvoidResult, EdfaPgCreditvoidResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgCreditvoidResult> {
        val creditvoidResult = EdfaPgCreditvoidResult.Success(context.parse(jsonObject))
        return EdfaPgResponse.Result(creditvoidResult, jsonObject)
    }
}
