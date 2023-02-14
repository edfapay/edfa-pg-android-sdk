/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.deserializer

import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.ExpresspayResponse
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResponse
import com.expresspay.sdk.model.response.sale.ExpresspaySaleResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter].
 * @see ExpresspaySaleResult
 * @see ExpresspaySaleResponse
 */
class ExpresspaySaleDeserializer : ExpresspayBaseDeserializer<ExpresspaySaleResult, ExpresspaySaleResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): ExpresspayResponse.Result<ExpresspaySaleResult> {
        val status = jsonObject["status"].asString
        val result = jsonObject["result"].asString

        val saleResult = if (status.equals(ExpresspayStatus.SECURE_3D.status, ignoreCase = true)) {
            ExpresspaySaleResult.Secure3d(context.parse(jsonObject))

        } else if(status.equals(ExpresspayStatus.REDIRECT.status, ignoreCase = true)){
            ExpresspaySaleResult.Redirect(context.parse(jsonObject))
        }else{
            if (result.equals(ExpresspayResult.DECLINED.result, ignoreCase = true)) {
                ExpresspaySaleResult.Decline(context.parse(jsonObject))
            } else {
                if (jsonObject.has("recurring_token")) {
                    ExpresspaySaleResult.Recurring(context.parse(jsonObject))
                } else {
                    ExpresspaySaleResult.Success(context.parse(jsonObject))
                }
            }
        }

        return ExpresspayResponse.Result(saleResult, jsonObject)
    }
}
