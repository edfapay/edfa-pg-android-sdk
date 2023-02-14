/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.expresspay.sdk.views.expresscardpay.creditcardview.animations

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.expresspay.sdk.views.expresscardpay.creditcardview.CreditCardView

/**
 * [Animation] extension that animates the view using a rotation
 */
open class RotationAnimation(
    val startPoint: Int = LEFT,
    time: Long = 500,
    val zDistance: Float = 50F
) : Animation(time) {
    override fun animate(
        frontView: View,
        backView: View,
        card: CreditCardView
    ): AnimatorSet {
        with(card) {
            val outView = if (isFlipped) backView else frontView
            val inView = if (isFlipped) frontView else backView
            val animators = mutableListOf<Animator>(
                ObjectAnimator.ofFloat(
                    outView,
                    "alpha",
                    1F, 0F
                ).apply {
                    startDelay = time / 2
                    duration = 0
                },
                ObjectAnimator.ofFloat(
                    inView,
                    "alpha",
                    1F, 0F
                ).apply {
                    duration = 0
                },
                ObjectAnimator.ofFloat(
                    inView,
                    "alpha",
                    0F, 1F
                ).apply {
                    startDelay = time / 2
                    duration = 0
                }
            )

            with(width * zDistance) {
                outView.cameraDistance = this
                inView.cameraDistance = this
            }

            when (startPoint) {
                RIGHT -> {
                    animators.addAll(
                        ObjectAnimator.ofFloat(
                            outView,
                            "rotationY",
                            0F, 180F
                        ).apply {
                            interpolator = AccelerateDecelerateInterpolator()
                            duration = time
                        },
                        ObjectAnimator.ofFloat(
                            inView,
                            "rotationY",
                            180F, 360F
                        ).apply {
                            interpolator = AccelerateDecelerateInterpolator()
                            duration = time
                        }
                    )
                }

                LEFT -> {
                    animators.addAll(
                        ObjectAnimator.ofFloat(
                            outView,
                            "rotationY",
                            0F, -180F
                        ).apply {
                            interpolator = AccelerateDecelerateInterpolator()
                            duration = time
                        },
                        ObjectAnimator.ofFloat(
                            inView,
                            "rotationY",
                            -180F, -360F
                        ).apply {
                            interpolator = AccelerateDecelerateInterpolator()
                            duration = time
                        }
                    )
                }
            }

            return AnimatorSet().apply {
                playTogether(animators)
            }
        }
    }

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
    }
}