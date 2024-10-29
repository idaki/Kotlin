package com.serdicagrid.serdicaweatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
 import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme


import androidx.lifecycle.ViewModelProvider
import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize WeatherService and WeatherRepository
        val weatherService = WeatherService()
        val repository = WeatherRepository(weatherService)

        // Create the ViewModel using MainViewModelFactory
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContent {
            SerdicaWeatherAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}