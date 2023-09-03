/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.deserializer

import com.edfapaygw.sdk.model.api.EdfaPgResult
import com.edfapaygw.sdk.model.api.EdfaPgStatus
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse
import com.edfapaygw.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapaygw.sdk.model.response.sale.EdfaPgSaleResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapaygw.sdk.feature.adapter.EdfaPgSaleAdapter].
 * @see EdfaPgSaleResult
 * @see EdfaPgSaleResponse
 */
class EdfaPgSaleDeserializer : EdfaPgBaseDeserializer<EdfaPgSaleResult, EdfaPgSaleResponse>() {

    override fun parseResultResponse(
        context: JsonDeserializationContext,
        jsonObject: JsonObject
    ): EdfaPgResponse.Result<EdfaPgSaleResult> {
        val status = jsonObject["status"].asString
        val result = jsonObject["result"].asString

        val saleResult = if (status.equals(EdfaPgStatus.SECURE_3D.status, ignoreCase = true)) {
            EdfaPgSaleResult.Secure3d(context.parse(jsonObject))

        } else if(status.equals(EdfaPgStatus.REDIRECT.status, ignoreCase = true)){
            EdfaPgSaleResult.Redirect(context.parse(jsonObject))
        }else{
            if (result.equals(EdfaPgResult.DECLINED.result, ignoreCase = true)) {
                EdfaPgSaleResult.Decline(context.parse(jsonObject))
            } else {
                if (jsonObject.has("recurring_token")) {
                    EdfaPgSaleResult.Recurring(context.parse(jsonObject))
                } else {
                    EdfaPgSaleResult.Success(context.parse(jsonObject))
                }
            }
        }

        return EdfaPgResponse.Result(saleResult, jsonObject)
    }
}
