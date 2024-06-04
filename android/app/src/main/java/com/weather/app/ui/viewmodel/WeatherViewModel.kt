package com.weather.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.model.WeatherResponse
import com.weather.app.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)

    val weather: StateFlow<WeatherResponse?> = _weather

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            _weather.value = repository.getWeather(city, apiKey)
        }
    }
}