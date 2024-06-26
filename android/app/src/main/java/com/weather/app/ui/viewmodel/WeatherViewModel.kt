package com.weather.app.ui.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.model.WeatherResponse
import com.weather.app.repository.WeatherRepository
import com.weather.app.utils.isInternetAvailable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.content.Context
import kotlinx.coroutines.launch

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val weatherResponse: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> get() = _weatherState

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                _weatherState.value = WeatherState.Loading
                val response = repository.getWeather(city, apiKey)
                _weatherState.value = WeatherState.Success(response)
            } catch (e: Exception) {
                if (e.message?.contains("HTTP 404") == true) {
                    _weatherState.value = WeatherState.Error("City not found")
                    return@launch
                }
                // handle internet connection error
                _weatherState.value =
                    WeatherState.Error("Failed to load weather data: ${e.message}")
            }
        }
    }

}