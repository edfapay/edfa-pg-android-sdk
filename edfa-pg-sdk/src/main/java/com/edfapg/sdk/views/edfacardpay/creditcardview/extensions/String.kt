/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.edfapg.sdk.views.edfacardpay.creditcardview.extensions

/**
 * Returns whether or not this string is numeric,
 * which means that it contains numbers only
 */
internal fun String.isNumeric(): Boolean {
    return Regex("[0-9]+").matches(this)
}