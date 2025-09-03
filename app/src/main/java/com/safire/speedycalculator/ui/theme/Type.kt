package com.safire.speedycalculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.safire.speedycalculator.R

val vendSans =FontFamily(
    Font(R.font.vend_sans)
)
val AppTypography = Typography(
    titleMedium = Typography().titleMedium.copy(
        fontFamily = vendSans,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        letterSpacing = 4.sp
    ),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = vendSans),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = vendSans, fontSize = 44.sp),
    bodySmall = Typography().bodySmall.copy(fontFamily = vendSans),
    labelLarge = Typography().labelLarge.copy(fontFamily = vendSans),
    labelMedium = Typography().labelMedium.copy(fontFamily = vendSans),
    labelSmall = Typography().labelSmall.copy(fontFamily = vendSans),


)

