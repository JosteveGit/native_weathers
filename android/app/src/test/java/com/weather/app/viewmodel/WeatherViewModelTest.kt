package com.weather.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.weather.app.model.WeatherResponse
import com.weather.app.repository.WeatherRepository
import com.weather.app.ui.viewmodel.WeatherState
import com.weather.app.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun `getWeather updates weatherState with success`() = runTest {
        val city = "London"
        val apiKey = "KEY"

        val mockWeatherResponse = WeatherResponse(
            weather = listOf(WeatherResponse.Weather("Clear")),
            main = WeatherResponse.Main(290.0)
        )
        Mockito.`when`(repository.getWeather(city, apiKey)).thenReturn(mockWeatherResponse)

        viewModel.getWeather(city, apiKey)

        val state = viewModel.weatherState.value
        assert(state is WeatherState.Success && state.weatherResponse == mockWeatherResponse)

    }

    @Test
    fun `getWeather updates weatherState with error`() = runTest {
        val city = "London"
        val apiKey = "FAKE KEY"

        val errorMessage = "HTTP 401 Unauthorized"

        Mockito.`when`(repository.getWeather(city, apiKey))
            .thenThrow(RuntimeException(errorMessage))

        viewModel.getWeather(city, apiKey)

        val state = viewModel.weatherState.value
        assert(state is WeatherState.Error && state.message == "Failed to load weather data: $errorMessage")

    }
}

