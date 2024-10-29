package com.serdicagrid.serdicaweatherapp.ui

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherService = WeatherService()
        val repository = WeatherRepository(weatherService)

        setContent {
            SerdicaWeatherAppTheme {
                MainScreen(repository)
            }
        }
    }
}
