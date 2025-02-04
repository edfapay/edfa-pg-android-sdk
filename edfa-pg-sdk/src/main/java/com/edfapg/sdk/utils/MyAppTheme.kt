package com.edfapg.sdk.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.edfapg.sdk.R


// Define custom font family
val AlRajhiFont = FontFamily(
    Font(R.font.changa_variable_font_wght, FontWeight.Normal),
)

// Set custom typography
val AppTypography = Typography(
    bodyLarge = TextStyle(fontFamily = AlRajhiFont, fontSize = 16.sp),
    titleLarge = TextStyle(fontFamily = AlRajhiFont, fontSize = 30.sp, fontWeight = FontWeight.Bold),
    labelSmall = TextStyle(fontFamily = AlRajhiFont, fontSize = 15.sp)
)
@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography, // Apply your custom typography
        content = content
    )
}