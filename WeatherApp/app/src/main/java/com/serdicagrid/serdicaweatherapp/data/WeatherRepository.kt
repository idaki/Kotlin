package com.serdicagrid.serdicaweatherapp.data

import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.model.WeatherData

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getWeather(lat: Double, lon: Double): WeatherData {
        return weatherService.fetchWeather(lat, lon)
    }
}