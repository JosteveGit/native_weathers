package com.weather.app.repository

import com.weather.app.network.WeatherApiService

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getWeather(city: String, apiKey: String) = apiService.getWeather(city, apiKey)
}