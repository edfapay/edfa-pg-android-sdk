/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.expresspay.sdk.views.expresscardpay.creditcardview.models

import com.expresspay.sdk.views.expresscardpay.creditcardview.extensions.isNumeric
import java.text.SimpleDateFormat
import java.util.*

/**
 * Object that represents a credit card
 */
open class CreditCard : Cloneable {
    /**
     * Name of the holder on the front of the credit card
     */
    var holder: String

    /**
     * Digits on the front of the credit card
     */
    var number: String
        set(value) {
            field = value

            brand = Brand.parse(value.replace(" ", "").trim())
        }

    /**
     * 3 digits number on the back of the credit card
     */
    var cvv: String

    /**
     * Expiration date on the back of the credit card
     */
    var expiry: String

    fun expiryMonth():Int?{
        if(expiry.length >= 2){
            return  expiry.substring(0,2).toInt()
        }
        return null
    }

    fun expiryYear():Int?{
        if(expiry.length == 4){
            return  expiry.substring(2,4).toInt() + 2000
        }
        return null
    }

    /**
     * Pin code used to confirm payments or withdraw money,
     *
     * This code is not necessary used, you should ask the user this code only if you're
     * building a credit card vault or generic credential vault
     */
    var pinCode: String

    /**
     * Brand of the credit card, this field is read-only and will change based on the [number]
     */
    var brand: Brand

    constructor(card: CreditCard) : this(
        card.holder,
        card.number,
        card.cvv,
        card.expiry,
        card.pinCode
    )

    @JvmOverloads
    constructor(
        holder: String = "",
        number: String = "",
        cvv: String = "",
        expiry: String = "",
        pinCode: String = ""
    ) {
        this.brand = Brand.GENERIC
        this.holder = holder
        this.number = number
        this.cvv = cvv
        this.expiry = expiry
        this.pinCode = pinCode
    }

    /**
     * Checks if the card's number only contains numbers and have valid length
     */
    fun isNumberValid(): Boolean {
        val number_ = number.replace(" ", "")
        return number_.trim().isNumeric() && number_.length == 16
    }

    /**
     * Checks if the expiry is valid, both month and year must higher then  current
     */
    fun isExpiryValid(): Boolean {

        return try {
            val seperator = this.expiry.length - 4

            val month = this.expiry.substring(0, 2).toInt()
            val year = this.expiry.substring(2+seperator, this.expiry.length).toInt()

            val month_2 = SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt()
            val year_2 = SimpleDateFormat("yy", Locale.getDefault()).format(Date()).toInt()

            return year+month >= year_2+month_2
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Checks if the CVV is valid, a valid CVV must be 3 digits long and contain numbers only
     */
    fun isCvvValid(): Boolean {
        return cvv.length == 3 && cvv.isNumeric()
    }

    override fun toString(): String {
        return "Holder: $holder, Number: $number, Expiry: $expiry, CVV: $cvv"
    }

    var unformattedNumber: String = ""
        get() = number.replace(" ", "").trim()


    override fun equals(other: Any?): Boolean {
        other?.let {
            it as CreditCard

            return it.holder == holder &&
                    it.number == number &&
                    it.cvv == cvv &&
                    it.expiry == expiry
        }

        return false
    }

    override fun clone(): Any {
        return CreditCard(
            holder,
            number,
            cvv,
            expiry
        )
    }
}