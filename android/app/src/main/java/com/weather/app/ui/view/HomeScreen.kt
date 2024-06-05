package com.weather.app.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.theme.always42808A
import com.weather.app.ui.theme.always74a3a9
import com.weather.app.ui.theme.appFont
import com.weather.app.ui.view.components.CustomButton
import com.weather.app.ui.viewmodel.HomeViewModel
import com.weather.app.utils.isInternetAvailable

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
    val favoriteCity = sharedPreferences.getString("favoriteCity", "")

    val city by homeViewModel.city
    var isCityInitialized by remember { mutableStateOf(false) }

    if (!isCityInitialized && city.isEmpty() && favoriteCity?.isNotEmpty() == true) {
        homeViewModel.setCity(favoriteCity)
        isCityInitialized = true
    }


    val keyboardController = LocalSoftwareKeyboardController.current


    val gradient = Brush.linearGradient(
        colors = listOf(Color.White, always74a3a9), start = Offset(0f, 0f), end = Offset(0f, 400f)
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(always42808A)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            BasicTextField(
                value = city,
                onValueChange = { homeViewModel.setCity(it) },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 60.sp,
                    fontFamily = appFont,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("cityTextField"),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions {

                    keyboardController?.hide()
                }

            ) { innerTextField ->
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)
                ) {
                    if (city.isEmpty()) {
                        Text(
                            text = "City",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 60.sp,
                            fontFamily = appFont,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = gradient,
                            )
                        )
                    }
                    innerTextField()
                }
            }
            Text(
                text = "Enter city",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = appFont,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(text = "Continue") {
                if (!isInternetAvailable(context)) {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                    return@CustomButton
                }
                if (city.isNotBlank()) {
                    navController.navigate("weather_detail/$city")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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