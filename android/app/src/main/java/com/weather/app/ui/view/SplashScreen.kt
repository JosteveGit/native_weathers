package com.weather.app.ui.view

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.view.components.GradientText

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF42808A))
    ) {
        GradientText(
            text = "Weather ",
            fontSize = 70
        )
    }

    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate("home")
    }, 2000)
}

@Preview
@Composable
fun SplashScreenPreview() {
    WeatherTheme {
        SplashScreen(rememberNavController())
    }
}