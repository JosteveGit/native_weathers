package com.weather.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weather.app.di.appModule
import com.weather.app.ui.theme.WeatherTheme
import com.weather.app.ui.view.HomeScreen
import com.weather.app.ui.view.SplashScreen
import com.weather.app.ui.view.WeatherDetailScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
            WeatherTheme {
              val navController = rememberNavController()

                NavHost(navController, startDestination = "splash"){
                    composable("splash"){
                        SplashScreen(navController)
                    }
                    composable("home"){
                        HomeScreen(navController)
                    }
                    composable("weather_detail/{city}"){
                        val city = it.arguments?.getString("city")
                        WeatherDetailScreen(city = city!!, navController = navController)
                    }
                }
            }
        }
    }
}
