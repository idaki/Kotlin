package com.serdicagrid.serdicaweatherapp.data

import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.model.WeatherData

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherData? {
        return weatherService.fetchCurrentWeather(lat, lon)
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double): List<WeatherData>? {
        return weatherService.fetchForecast(lat, lon)
    }
}
