package com.serdicagrid.serdicaweatherapp.data

import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.model.WeatherResponse

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponse? {
        return weatherService.fetchCurrentWeather(lat, lon)
    }
}
