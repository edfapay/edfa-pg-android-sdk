/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.feature.service

import com.edfapg.sdk.model.response.sale.EdfaPgRedirectParams
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface Get3dsHtmlService {

    @POST
    fun getHtml(@Url url: String, @Body redirectParams: EdfaPgRedirectParams): Call<String>
}
