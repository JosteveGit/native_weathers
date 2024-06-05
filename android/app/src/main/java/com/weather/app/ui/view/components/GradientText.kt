package com.weather.app.ui.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.app.R

@Composable
fun GradientText(
    text: String
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color.White, Color(0xFF74a3a9)),
        start = Offset(0f, 0f),
        end = Offset(0f, 400f)
    )

    val customFontFamily = FontFamily(
        Font(R.font.custom)
    )

    Text(
        text = text,
        fontSize = 130.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            brush = gradient,
            fontFamily = customFontFamily,
            textAlign = TextAlign.Center
        )
    )
}