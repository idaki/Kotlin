package com.serdicagrid.serdicaweatherapp.ui

import com.serdicagrid.serdicaweatherapp.model.WeatherResponse

sealed class WeatherUIState {
    object Loading : WeatherUIState()
    data class Success(val weatherResponse: WeatherResponse) : WeatherUIState() // Use weatherResponse here
    data class Error(val message: String) : WeatherUIState()
}
