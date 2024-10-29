package com.serdicagrid.serdicaweatherapp.ui

import com.serdicagrid.serdicaweatherapp.model.WeatherData


sealed class WeatherUIState {
    object Loading : WeatherUIState()
    data class Success(val weatherData: WeatherData) : WeatherUIState()
    data class Error(val message: String) : WeatherUIState()
}
