/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.creditvoid.ExpresspayCreditvoidResponse
import com.expresspay.sdk.model.response.creditvoid.ExpresspayCreditvoidResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.expresspay.sdk.feature.adapter.ExpresspayCreditvoidAdapter].
 * @see ExpresspayCreditvoidResult
 * @see ExpresspayCreditvoidResponse
 */
class ExpresspayCreditvoidDeserializer :
    ExpresspayBaseDeserializer<ExpresspayCreditvoidResult, ExpresspayCreditvoidResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<ExpresspayCreditvoidResult> {
        val creditvoidResult = ExpresspayCreditvoidResult.Success(context.parse(jsonObject))
        return ExpresspayResponse.Result(creditvoidResult)
    }
}
