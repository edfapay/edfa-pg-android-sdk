/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.deserializer

import com.edfapaygw.sdk.model.api.EdfaPgResult
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * The base EdfaPg [JsonDeserializer] to properly manage the response: result or error.
 * The end-user response is presenter by the [EdfaPgResponse].
 * @see com.edfapaygw.sdk.feature.adapter.EdfaPgBaseAdapter
 *
 * @param Result the success result type for [Response].
 * @param Response the response type.
 */
abstract class EdfaPgBaseDeserializer<Result, Response : EdfaPgResponse<Result>> : JsonDeserializer<Response> {

    companion object {
        private const val RESULT = "result"
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Response? {
        if (context == null) return null
        val jsonObject = json?.asJsonObject ?: return null

        val result = jsonObject[RESULT].asString
        return if (result.equals(EdfaPgResult.ERROR.result, ignoreCase = true)) {
            parseErrorResponse(context, jsonObject) as Response
        } else {
            parseResultResponse(context, jsonObject) as Response
        }
    }

    /**
     * Parse the generic response to the [EdfaPgResponse.Error].
     * @see com.edfapaygw.sdk.model.response.base.error.EdfaPgError
     */
    private fun parseErrorResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Error<Result> {
        val error =  EdfaPgResponse.Error<Result>(
            context.parse(jsonObject),
            jsonObject
        )
        return error
    }

    /**
     * Parse the generic response to the [EdfaPgResponse.Result]. Required to override.
     * @see com.edfapaygw.sdk.model.response.base.result.IEdfaPgResult
     */
    protected abstract fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<Result>

    /**
     * Extension to parse the [JsonObject] by [ParseClass] type.
     *
     * @param ParseClass the class type to parse.
     * @param jsonObject the actual JSON.
     */
    protected inline fun <reified ParseClass> JsonDeserializationContext.parse(jsonObject: JsonObject) =
        deserialize<ParseClass>(jsonObject, ParseClass::class.java)
}
