/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.deserializer

import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResponse
import com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * Custom deserializer for the [com.edfapg.sdk.feature.adapter.EdfaPgSadadAdapter].
 * @see EdfaPgSadadResult
 * @see EdfaPgSadadResponse
 */
class EdfaPgSadadDeserializer :
    EdfaPgBaseDeserializer<EdfaPgSadadResult, EdfaPgSadadResponse>() {

    companion object {
        private const val RESULT = "data"
        private const val CODE = "code"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): EdfaPgSadadResponse? {
        if (context == null) return null
        val jsonObject = json?.asJsonObject ?: return null

        val code = jsonObject[CODE].asInt
        val result = jsonObject[RESULT].asJsonObject
        return when(code){
            in 200..299 -> {
                parseResultResponse(context, result)
            }
            else -> {
                parseErrorResponse(context, jsonObject)
            }
        }
    }

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgSadadResult> {
        val result = EdfaPgSadadResult.Success(context.parse(jsonObject))
        return EdfaPgResponse.Result(result, jsonObject)
    }
}
