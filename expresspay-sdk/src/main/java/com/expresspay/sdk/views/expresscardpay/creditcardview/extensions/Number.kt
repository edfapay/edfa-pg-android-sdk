/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.expresspay.sdk.views.expresscardpay.creditcardview.extensions

import android.content.res.Resources

/**
 * Converts a pixel size into a DP size
 */
internal val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts a DP size into a pixel size
 */
internal val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts a pixel size into a DP size
 */
internal val Float.dp: Float
    get() = this / Resources.getSystem().displayMetrics.density

/**
 * Converts a DP size into a pixel size
 */
internal val Float.px: Float
    get() = this * Resources.getSystem().displayMetrics.density

/**
 * Converts a pixel size into an SP size
 */
internal val Float.sp: Float
    get() = this / Resources.getSystem().displayMetrics.scaledDensity