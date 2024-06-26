package com.example.bookshelf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bookshelf.R

// Set of Material typography styles to start with

val nanumSquareRound = FontFamily(
    Font(R.font.nanum_square_round_b, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nanum_square_round_eb, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.nanum_square_round_l, FontWeight.Light, FontStyle.Normal),
    Font(R.font.nanum_square_round_r, FontWeight.Normal, FontStyle.Normal),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = nanumSquareRound,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)