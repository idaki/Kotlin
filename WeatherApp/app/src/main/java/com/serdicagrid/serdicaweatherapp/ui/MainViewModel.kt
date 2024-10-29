package com.serdicagrid.serdicaweatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: WeatherRepository) : ViewModel() {

    // StateFlow to hold the current weather data
    private val _currentWeatherState = MutableStateFlow<WeatherData?>(null)
    val currentWeatherState: StateFlow<WeatherData?> = _currentWeatherState

    // StateFlow to hold the weather forecast
    private val _forecastWeatherState = MutableStateFlow<List<WeatherData>?>(null)
    val forecastWeatherState: StateFlow<List<WeatherData>?> = _forecastWeatherState

    // Function to load current weather
    fun loadCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val weatherData = repository.getCurrentWeather(lat, lon)
            _currentWeatherState.value = weatherData
        }
    }

    // Function to load weather forecast
    fun loadWeatherForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            val forecastData = repository.getWeatherForecast(lat, lon)
            _forecastWeatherState.value = forecastData
        }
    }
}
