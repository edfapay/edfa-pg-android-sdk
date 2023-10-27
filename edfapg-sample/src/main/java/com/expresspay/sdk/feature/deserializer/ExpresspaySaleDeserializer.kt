/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.deserializer

import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.EdfaPgResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResult
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject

/**
 * Custom deserializer for the [com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter].
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
        } else {
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

        return EdfaPgResponse.Result(saleResult)
    }
}
