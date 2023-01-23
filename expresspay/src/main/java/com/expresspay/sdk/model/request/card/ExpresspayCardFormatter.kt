/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.card

import java.text.DecimalFormat

/**
 * The [ExpresspayCard] values formatter.
 */
internal class ExpresspayCardFormatter {

    private val EXPIRE_MONTH_FORMAT = DecimalFormat("00")

    /**
     * Validate and format the [ExpresspayCard.expireMonth].
     *
     * @param card the [ExpresspayCard].
     */
    fun expireMonthFormat(card: ExpresspayCard): String = EXPIRE_MONTH_FORMAT.format(card.expireMonth)

    /**
     * Validate and format the [ExpresspayCard.expireYear].
     *
     * @param card the [ExpresspayCard].
     */
    fun expireYearFormat(card: ExpresspayCard): String = card.expireYear.toString()
}
