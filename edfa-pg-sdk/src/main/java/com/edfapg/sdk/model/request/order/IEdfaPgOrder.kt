/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.order

import androidx.annotation.NonNull
import com.edfapg.sdk.model.request.Extra

/**
 * The base order data holder description.
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 */
interface IEdfaPgOrder {

    /**
     * Transaction ID in the Merchants system. String up to 255 characters.
     */
    val id: String

    /**
     * The amount of the transaction. Numbers in the form XXXX.XX (without leading zeros).
     */
    val amount: Double

    /**
     * Description of the transaction (product name). String up to 1024 characters.
     */
    val description: String
}
