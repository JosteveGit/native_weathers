package com.weather.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weather.app.model.WeatherResponse
import com.weather.app.repository.WeatherRepository
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.view.HomeScreen
import com.weather.app.ui.view.WeatherDetailScreen
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class WeatherIntegrationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testWeatherFlow() {
        // Mock the repository and inject it
        val mockRepository = Mockito.mock(WeatherRepository::class.java)
        val mockWeatherResponse = WeatherResponse(
            weather = listOf(WeatherResponse.Weather("Clear")),
            main = WeatherResponse.Main(290.0)
        )
        runBlocking {
            Mockito.`when`(mockRepository.getWeather("London", "KEY"))
                .thenReturn(mockWeatherResponse)
        }

        loadKoinModules(
            module {
                single { mockRepository }
            }
        )

        composeTestRule.setContent {
            WeatherTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController = navController) }
                    composable("weather_detail/{city}") { backStackEntry ->
                        val city = backStackEntry.arguments?.getString("city") ?: ""
                        WeatherDetailScreen(city, navController)
                    }
                }
            }
        }

        // Enter a city name
        composeTestRule.onNodeWithText("Enter city name").performTextInput("London")

        // Click the Get Weather button
        composeTestRule.onNodeWithText("Get Weather").performClick()

        // Check if the WeatherDetailScreen is displayed with the mocked data
        composeTestRule.onNodeWithText("City: London").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description: Clear").assertIsDisplayed()
        composeTestRule.onNodeWithText("Temperature: 16.85 °C").assertIsDisplayed()
    }
}
