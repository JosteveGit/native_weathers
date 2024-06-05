package com.weather.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class HomeViewModel : ViewModel() {
    private val _city = mutableStateOf("")
    val city: State<String> = _city

    fun setCity(newCity: String) {
        _city.value = newCity
    }
}
