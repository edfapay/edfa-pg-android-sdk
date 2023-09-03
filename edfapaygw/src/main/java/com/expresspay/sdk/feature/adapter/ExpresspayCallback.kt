/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.feature.adapter

import com.edfapaygw.sdk.model.response.base.error.EdfaPgError
import com.edfapaygw.sdk.model.response.base.EdfaPgResponse

/**
 * The EdfaPg Callback for the [EdfaPgBaseAdapter] and its extenders.
 *
 * @param Result the successful result type of the [Response].
 * @param Response the response of the request type.
 */
interface EdfaPgCallback<Result, Response : EdfaPgResponse<Result>> {

    /**
     * The custom success result callback. Required to override.
     * Called after the [onResponse] in case the response is [EdfaPgResponse.Result].
     *
     * @param result the [Result].
     */
    fun onResult(result: Result)

    /**
     * The custom error result callback. Required to override.
     * Called after the [onResponse] in case the response is [EdfaPgResponse.Error].
     *
     * @param error the [EdfaPgError].
     */
    fun onError(error: EdfaPgError)

    /**
     * The unhandled exception callback. Optional to override.
     *
     * @param throwable the [Throwable].
     */
    fun onFailure(throwable: Throwable) = Unit

    /**
     * The custom success response callback: result or error. Optional to override.
     * @see EdfaPgResponse
     *
     * @param response the [Response].
     */
    fun onResponse(response: Response) = Unit
}
