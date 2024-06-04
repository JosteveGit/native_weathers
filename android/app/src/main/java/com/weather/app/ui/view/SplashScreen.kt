package com.weather.app.ui.view

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Weather App", style = MaterialTheme.typography.displayLarge)
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