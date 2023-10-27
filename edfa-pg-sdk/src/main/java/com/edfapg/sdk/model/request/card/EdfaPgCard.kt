/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.request.card

import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.annotation.Size
import com.edfapg.sdk.toolbox.EdfaPgValidation.Card
import java.io.Serializable

/**
 * The required card data holder.
 * For the test purposes use [EdfaPgTestCard].
 * @see com.edfapg.sdk.feature.adapter.EdfaPgSaleAdapter
 *
 * @property number the credit card number.
 * @property expireMonth the month of expiry of the credit card. Month in the form XX.
 * @property expireYear the year of expiry of the credit card. Year in the form XXXX.
 * @property cvv the CVV/CVC2 credit card verification code. 3-4 symbols.
 */
data class EdfaPgCard(
    @NonNull
    @Size(min = Card.CARD_NUMBER_MIN, max = Card.CARD_NUMBER_MAX)
    val number: String,
    @NonNull
    @IntRange(from = Card.MONTH_MIN, to = Card.MONTH_MAX)
    val expireMonth: Int,
    @NonNull
    val expireYear: Int,
    @NonNull
    @Size(min = Card.CVV_MIN, max = Card.CVV_MAX)
    val cvv: String,
) : Serializable
