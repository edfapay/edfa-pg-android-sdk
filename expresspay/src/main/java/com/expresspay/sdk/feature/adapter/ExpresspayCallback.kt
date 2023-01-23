/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.feature.adapter

import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.base.ExpresspayResponse

/**
 * The Expresspay Callback for the [ExpresspayBaseAdapter] and its extenders.
 *
 * @param Result the successful result type of the [Response].
 * @param Response the response of the request type.
 */
interface ExpresspayCallback<Result, Response : ExpresspayResponse<Result>> {

    /**
     * The custom success result callback. Required to override.
     * Called after the [onResponse] in case the response is [ExpresspayResponse.Result].
     *
     * @param result the [Result].
     */
    fun onResult(result: Result)

    /**
     * The custom error result callback. Required to override.
     * Called after the [onResponse] in case the response is [ExpresspayResponse.Error].
     *
     * @param error the [ExpresspayError].
     */
    fun onError(error: ExpresspayError)

    /**
     * The unhandled exception callback. Optional to override.
     *
     * @param throwable the [Throwable].
     */
    fun onFailure(throwable: Throwable) = Unit

    /**
     * The custom success response callback: result or error. Optional to override.
     * @see ExpresspayResponse
     *
     * @param response the [Response].
     */
    fun onResponse(response: Response) = Unit
}
