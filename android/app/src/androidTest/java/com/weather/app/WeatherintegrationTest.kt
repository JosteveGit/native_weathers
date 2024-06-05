package com.weather.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weather.app.model.WeatherResponse
import com.weather.app.network.WeatherApiService
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
        composeTestRule.waitUntil(
            condition = {
                composeTestRule.onAllNodesWithText("Enter city").fetchSemanticsNodes()
                    .isNotEmpty()
            },
            timeoutMillis = 5000
        )

        composeTestRule.onNodeWithTag("cityTextField").performTextInput("London")
        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText("London").fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithText("London").isDisplayed()
        composeTestRule.onNodeWithText("Save as favorite").isDisplayed()
    }
}
