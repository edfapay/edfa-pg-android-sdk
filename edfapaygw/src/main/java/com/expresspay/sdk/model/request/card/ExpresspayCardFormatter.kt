/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.model.request.card

import java.text.DecimalFormat

/**
 * The [EdfaPgCard] values formatter.
 */
internal class EdfaPgCardFormatter {

    private val EXPIRE_MONTH_FORMAT = DecimalFormat("00")

    /**
     * Validate and format the [EdfaPgCard.expireMonth].
     *
     * @param card the [EdfaPgCard].
     */
    fun expireMonthFormat(card: EdfaPgCard): String = EXPIRE_MONTH_FORMAT.format(card.expireMonth)

    /**
     * Validate and format the [EdfaPgCard.expireYear].
     *
     * @param card the [EdfaPgCard].
     */
    fun expireYearFormat(card: EdfaPgCard): String = card.expireYear.toString()
}
