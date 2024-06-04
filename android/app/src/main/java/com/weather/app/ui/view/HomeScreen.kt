package com.weather.app.ui.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
    val favoriteCity = sharedPreferences.getString("favoriteCity", "")

    var city by remember { mutableStateOf(favoriteCity ?: "") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = {
                Text("Enter city name")
            }
        )
        Button(onClick = {
            if (city.isNotBlank()) {
                navController.navigate("weather_detail/$city")
            }
        }) {
            Text("Get Weather")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeatherTheme {
        HomeScreen(rememberNavController())
    }
}