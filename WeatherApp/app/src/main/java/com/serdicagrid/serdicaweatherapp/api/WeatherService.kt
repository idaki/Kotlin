package com.serdicagrid.serdicaweatherapp.api

import com.serdicagrid.serdicaweatherapp.model.WeatherData


interface WeatherService {
    suspend fun fetchWeather(lat: Double, lon: Double): WeatherData
}