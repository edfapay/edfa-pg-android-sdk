package com.edfapg.sdk.utils

import android.view.View
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale
import androidx.core.text.layoutDirection

class CreditCardNumberVisualTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val isRtl = Locale.getDefault().layoutDirection == View.LAYOUT_DIRECTION_RTL
        val digits = text.text.filter { it.isDigit() }

        val chunks = digits.chunked(4)
        val transformed = when {
            isRtl -> chunks.reversed().joinToString(" ")
            else -> chunks.joinToString(" ")
        }

        return TransformedText(
            AnnotatedString(transformed),
            object : OffsetMapping {

                override fun originalToTransformed(offset: Int): Int {
                    val spacesBefore = (offset - 1).coerceAtLeast(0) / 4
                    return (offset + spacesBefore).coerceAtMost(transformed.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    val spacesBefore = offset / 5
                    return (offset - spacesBefore).coerceAtMost(digits.length)
                }
            }
        )
    }
}
