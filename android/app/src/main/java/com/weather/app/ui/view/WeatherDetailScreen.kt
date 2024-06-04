package com.weather.app.ui.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.viewmodel.WeatherState
import com.weather.app.ui.viewmodel.WeatherViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun WeatherDetailScreen(
    city: String,
    navController: NavHostController,
    weatherViewModel: WeatherViewModel = getViewModel()
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val apiKey = "eb05a02728f5edd17985006b4c42c07a"

    val weatherState by weatherViewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        weatherViewModel.getWeather(city, apiKey)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (weatherState) {
            is WeatherState.Loading -> {
                CircularProgressIndicator()
            }

            is WeatherState.Success -> {
                val weatherResponse = (weatherState as WeatherState.Success).weatherResponse
                Text("City: $city")
                Text("Description: ${weatherResponse.weather[0].description}")
                Text("Temperature: ${weatherResponse.main.temp - 273.15} °C")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    editor.putString("favorite_city", city)
                    editor.apply()
                    navController.navigate("home")
                }) {
                    Text("Save as Favorite")
                }
            }

            is WeatherState.Error -> {
                val message = (weatherState as WeatherState.Error).message
                Text("Error: $message")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailScreenPreview() {
    WeatherTheme {
        WeatherDetailScreen("London", rememberNavController())
    }
}