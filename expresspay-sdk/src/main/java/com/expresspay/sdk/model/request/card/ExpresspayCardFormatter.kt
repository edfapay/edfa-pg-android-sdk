/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.request.card

import com.expresspay.sdk.toolbox.ExpresspayAmountFormatter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

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
    fun expireMonthFormat(card: ExpresspayCard): String = card.expireMonth.toString().padStart(2, '0')//EXPIRE_MONTH_FORMAT.format(card.expireMonth)

    /**
     * Validate and format the [ExpresspayCard.expireYear].
     *
     * @param card the [ExpresspayCard].
     */
    fun expireYearFormat(card: ExpresspayCard): String = card.expireYear.toString()
}
