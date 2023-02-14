/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.capture.ExpresspayCaptureResponse
import com.expresspay.sdk.model.response.capture.ExpresspayCaptureResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.expresspay.sdk.feature.adapter.ExpresspayCaptureAdapter].
 * @see ExpresspayCaptureResult
 * @see ExpresspayCaptureResponse
 */
class ExpresspayCaptureDeserializer : ExpresspayBaseDeserializer<ExpresspayCaptureResult, ExpresspayCaptureResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<ExpresspayCaptureResult> {
        val result = jsonObject["result"].asString

        val captureResult = if (result.equals(ExpresspayResult.DECLINED.result, ignoreCase = true)) {
            ExpresspayCaptureResult.Decline(context.parse(jsonObject))
        } else {
            ExpresspayCaptureResult.Success(context.parse(jsonObject))
        }

        return  ExpresspayResponse.Result(captureResult, jsonObject)

    }
}
