package com.weather.app.repository

import com.weather.app.model.WeatherResponse
import com.weather.app.network.WeatherApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    @Mock
    private lateinit var apiService: WeatherApiService

    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = WeatherRepository(apiService)
    }

    @Test
    fun `getWeather returns weather data`() = runTest {
        val city = "London"
        val apiKey = "KEY"

        val mockWeatherResponse = WeatherResponse(
            weather = listOf(WeatherResponse.Weather("Clear")),
            main = WeatherResponse.Main(290.0)
        )

        Mockito.`when`(apiService.getWeather(city, apiKey)).thenReturn(mockWeatherResponse)

        val result = repository.getWeather(city, apiKey)

        assert(result == mockWeatherResponse)

    }
}
