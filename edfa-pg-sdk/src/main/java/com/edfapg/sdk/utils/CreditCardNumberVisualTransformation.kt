package com.edfapg.sdk.utils

import android.view.View
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale
import androidx.core.text.layoutDirection

class CreditCardNumberVisualTransformation() : VisualTransformation {

    fun transform(text: String) : String{
        val isRtl = Locale.getDefault().layoutDirection == View.LAYOUT_DIRECTION_RTL
        val digits = text.filter { it.isDigit() }

        val chunks = digits.chunked(4)
        return when {
            isRtl -> chunks.reversed().joinToString(" ")
            else -> chunks.joinToString(" ")
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val transformed = transform(original)

        return TransformedText(
            AnnotatedString(transformed),
            object : OffsetMapping {

                override fun originalToTransformed(offset: Int): Int {
                    val spacesBefore = (offset - 1).coerceAtLeast(0) / 4
                    return (offset + spacesBefore).coerceAtMost(transformed.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    val spacesBefore = offset / 5
                    return (offset - spacesBefore).coerceAtMost(original.length)
                }
            }
        )
    }
}