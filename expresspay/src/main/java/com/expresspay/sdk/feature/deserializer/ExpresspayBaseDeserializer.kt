/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * The base Expresspay [JsonDeserializer] to properly manage the response: result or error.
 * The end-user response is presenter by the [ExpresspayResponse].
 * @see com.expresspay.sdk.feature.adapter.ExpresspayBaseAdapter
 *
 * @param Result the success result type for [Response].
 * @param Response the response type.
 */
abstract class ExpresspayBaseDeserializer<Result, Response : ExpresspayResponse<Result>> : JsonDeserializer<Response> {

    companion object {
        private const val RESULT = "result"
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Response? {
        if (context == null) return null
        val jsonObject = json?.asJsonObject ?: return null

        val result = jsonObject[RESULT].asString
        return if (result.equals(ExpresspayResult.ERROR.result, ignoreCase = true)) {
            parseErrorResponse(context, jsonObject) as Response
        } else {
            parseResultResponse(context, jsonObject) as Response
        }
    }

    /**
     * Parse the generic response to the [ExpresspayResponse.Error].
     * @see com.expresspay.sdk.model.response.base.error.ExpresspayError
     */
    private fun parseErrorResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ) = ExpresspayResponse.Error<Result>(
        context.parse(jsonObject)
    )

    /**
     * Parse the generic response to the [ExpresspayResponse.Result]. Required to override.
     * @see com.expresspay.sdk.model.response.base.result.IExpresspayResult
     */
    protected abstract fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<Result>

    /**
     * Extension to parse the [JsonObject] by [ParseClass] type.
     *
     * @param ParseClass the class type to parse.
     * @param jsonObject the actual JSON.
     */
    protected inline fun <reified ParseClass> JsonDeserializationContext.parse(jsonObject: JsonObject) =
        deserialize<ParseClass>(jsonObject, ParseClass::class.java)
}
