package com.weather.app.ui.view

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.view.components.GradientText
import kotlinx.coroutines.delay

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

    LaunchedEffect(Unit) {
        delay(2000)
        Log.d("SplashScreen", "Navigating to home")
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    WeatherTheme {
        SplashScreen(rememberNavController())
    }
}