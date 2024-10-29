package com.serdicagrid.serdicaweatherapp.model

data class WeatherData(
    val temperature: Double,
    val humidity: Int,
    val condition: String,
    val timestamp: Long? = null

)
