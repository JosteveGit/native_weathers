package com.weather.app.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.theme.always42808A
import com.weather.app.ui.theme.appFont
import com.weather.app.ui.view.components.BackButton
import com.weather.app.ui.view.components.CustomButton
import com.weather.app.ui.view.components.GradientText
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


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(always42808A)
            .padding(18.dp)
    ) {
        when (weatherState) {
            is WeatherState.Loading -> {
                CircularProgressIndicator(
                    color = Color.White,
                )
            }

            is WeatherState.Success -> {
                val weatherResponse = (weatherState as WeatherState.Success).weatherResponse

                var description = weatherResponse.weather[0].description
                description =
                    description.replaceFirst(description[0], description[0].uppercaseChar())

                val newCity = city.replaceFirst(city[0], city[0].uppercaseChar())
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,

                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    BackButton {
                        navController.popBackStack()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = newCity,
                            color = Color.White,
                            fontWeight = FontWeight.W600,
                            fontSize = 30.sp,
                            modifier = Modifier.fillMaxWidth(),
                            fontFamily = appFont,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = description,
                            color = Color.White,
                            fontFamily = appFont,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        GradientText("${(weatherResponse.main.temp - 273.15).toInt()}°C")
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    CustomButton(
                        text = "Save as favorite",
                    ) {
                        editor.putString("favoriteCity", newCity)
                        editor.apply()
                        Toast.makeText(context, "$newCity saved as favorite", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is WeatherState.Error -> {
                val message = (weatherState as WeatherState.Error).message
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    BackButton {
                        navController.popBackStack()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(message, color = Color.White, fontFamily = appFont)
                    }
                }
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
