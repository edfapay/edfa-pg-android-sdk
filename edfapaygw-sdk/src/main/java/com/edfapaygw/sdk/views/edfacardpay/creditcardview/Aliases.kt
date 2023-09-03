/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.edfapaygw.sdk.views.edfacardpay.creditcardview

import android.content.res.XmlResourceParser
import android.graphics.Point
import com.edfapaygw.sdk.views.edfacardpay.creditcardview.models.Brand
import com.edfapaygw.sdk.views.edfacardpay.creditcardview.models.CardArea

/**
 * Basic empty callback
 */
typealias Callback = () -> Unit

/**
 * Advanced click listener that makes use of the clicked position and area
 */
typealias AreaClickListener = (
    card: CreditCardView,
    area: CardArea
) -> Unit

/**
 * Click listener for the [CreditCardView.setGridClickListener] method,
 * this gives the coordinates (x and y) on the grid you have specified and the clicked point
 * (the actual x and y on the screen)
 */
typealias GridClickListener = (
    card: CreditCardView,
    gridPosition: Point
) -> Unit

/**
 * Map where each Brand has its own card style
 */
typealias StyleMap = MutableMap<Brand, Int?>

/**
 * XML parser handler that should handle the parsing of a xml resource
 */
typealias XmlParserHandler<T> = (XmlResourceParser?) -> T