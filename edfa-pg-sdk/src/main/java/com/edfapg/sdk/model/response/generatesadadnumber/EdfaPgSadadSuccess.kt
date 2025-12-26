/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.response.gettransactionstatus

import androidx.annotation.NonNull
import com.edfapg.sdk.model.api.EdfaPgAction
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.base.result.IEdfaPgResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * The SADAD success result of the [com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResult].
 * @see com.edfapg.sdk.model.response.generatesadadnumber.EdfaPgSadadResponse
 */
data class EdfaPgSadadSuccess(
    val billNumber: String,
    val sadadNumber: String,
) : Serializable

/*
{"code":200,"message":"Success","errorCode":null,"data":{"billNumber":"8705cb74e6ed48cfabea","sadadNumber":"2032400452988001149"}}
* */
