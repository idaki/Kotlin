package com.serdicagrid.serdicaweatherapp.model

data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<WeatherCondition>,
    val main: MainWeather,
    val visibility: Int, // Visibility in meters
    val wind: Wind,      // Wind information
    val clouds: Clouds,  // Cloud coverage
    val dt: Long,        // Time of the report
    val timezone: String, // Timezone offset
    val name: String,     // City name
    val sys: SystemInfo   // System information (for sunrise and sunset times)
)

data class SystemInfo(
    val sunrise: Long, // Sunrise time
    val sunset: Long   // Sunset time
)

data class Coordinates(
    val lon: Double, // Longitude
    val lat: Double   // Latitude
)

data class WeatherCondition(
    val id: Int,      // Weather condition ID
    val main: String, // Main weather condition (e.g., Rain, Clouds)
    val description: String, // Detailed weather description
    val icon: String  // Weather icon ID
)

data class MainWeather(
    val temp: Double,     // Current temperature in Celsius
    val feelsLike: Double, // 'Feels like' temperature in Celsius
    val tempMin: Double,   // Minimum temperature in Celsius
    val tempMax: Double,   // Maximum temperature in Celsius
    val pressure: Int,     // Atmospheric pressure in hPa
    val humidity: Int      // Humidity percentage
)

data class Wind(
    val speed: Double, // Wind speed in m/s
    val deg: Int       // Wind direction in degrees
)

data class Clouds(
    val all: Int // Cloud coverage percentage
)
