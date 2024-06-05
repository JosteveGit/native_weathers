package com.weather.app.di

import com.weather.app.network.RetrofitInstance
import com.weather.app.repository.WeatherRepository
import com.weather.app.ui.viewmodel.HomeViewModel
import com.weather.app.ui.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.apiService }
    single { WeatherRepository(get()) }
    viewModel { WeatherViewModel(get()) }
    viewModel { HomeViewModel()}
}