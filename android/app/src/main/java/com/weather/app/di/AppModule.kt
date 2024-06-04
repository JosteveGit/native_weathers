package com.weather.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.apiService }
    single { WeatherRepository(get()) }
    viewModel { WeatherViewModel(get()) }
}