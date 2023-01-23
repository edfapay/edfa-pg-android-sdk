/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.order

/**
 * The base order data holder description.
 * @see com.expresspay.sdk.feature.adapter.ExpresspaySaleAdapter
 */
interface IExpresspayOrder {

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
