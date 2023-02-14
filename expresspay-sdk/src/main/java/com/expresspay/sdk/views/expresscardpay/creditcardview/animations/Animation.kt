/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.expresspay.sdk.views.expresscardpay.creditcardview.animations

import android.animation.AnimatorSet
import android.view.View
import com.expresspay.sdk.views.expresscardpay.creditcardview.CreditCardView

/**
 * Base animation class
 */
abstract class Animation(val time: Long) {
    /**
     * Animates the given [card]'s [frontView] and [backView]
     *
     * Keep in mind that [CreditCardView.isFlipped]'s value is changed after the animation is done
     */
    abstract fun animate(
        frontView: View,
        backView: View,
        card: CreditCardView
    ): AnimatorSet

    /**
     * Extension of Kotlin's [MutableCollection.addAll] which takes a variable number of elements
     * and adds them to the mutable list
     */
    protected fun <E> MutableCollection<E>.addAll(vararg elements: E): Boolean {
        for (e in elements) {
            if (!add(e)) {
                return false
            }
        }

        return true
    }
}