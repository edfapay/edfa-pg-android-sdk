/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.deserializer

import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.capture.EdfaPgCaptureResponse
import com.edfapg.sdk.model.response.capture.EdfaPgCaptureResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapg.sdk.feature.adapter.EdfaPgCaptureAdapter].
 * @see EdfaPgCaptureResult
 * @see EdfaPgCaptureResponse
 */
class EdfaPgCaptureDeserializer : EdfaPgBaseDeserializer<EdfaPgCaptureResult, EdfaPgCaptureResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgCaptureResult> {
        val result = jsonObject["result"].asString

        val captureResult = if (result.equals(EdfaPgResult.DECLINED.result, ignoreCase = true)) {
            EdfaPgCaptureResult.Decline(context.parse(jsonObject))
        } else {
            EdfaPgCaptureResult.Success(context.parse(jsonObject))
        }

        return  EdfaPgResponse.Result(captureResult, jsonObject)

    }
}
