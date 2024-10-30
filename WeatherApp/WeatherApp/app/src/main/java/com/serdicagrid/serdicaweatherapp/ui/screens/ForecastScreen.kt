package com.serdicagrid.serdicaweatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serdicagrid.serdicaweatherapp.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastContent(weatherData: WeatherResponse?, errorMessage: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (weatherData != null) {
            WeatherInfo(weatherData)
        } else if (errorMessage != null) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
        } else {
            Text("Loading weather data...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun WeatherInfo(weatherResponse: WeatherResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Location: ${weatherResponse.name}", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp))

        // Temperature Info Card
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text("Current Temperature: ${weatherResponse.main.temp}°C", style = MaterialTheme.typography.bodyLarge)
                Text("Feels Like: ${weatherResponse.main.feelsLike}°C", style = MaterialTheme.typography.bodyMedium)
                Text("Min Temperature: ${weatherResponse.main.tempMin}°C", style = MaterialTheme.typography.bodyMedium)
                Text("Max Temperature: ${weatherResponse.main.tempMax}°C", style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Horizontal Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Humidity and Pressure Info Card
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text("Humidity: ${weatherResponse.main.humidity}%", style = MaterialTheme.typography.bodyLarge)
                Text("Pressure: ${weatherResponse.main.pressure} hPa", style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Horizontal Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Wind Info Card
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text("Wind Speed: ${weatherResponse.wind.speed} m/s", style = MaterialTheme.typography.bodyLarge)
                Text("Wind Direction: ${weatherResponse.wind.deg}°", style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Horizontal Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Condition and Cloud Coverage Info Card
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text("Condition: ${weatherResponse.weather.firstOrNull()?.description ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                Text("Cloud Coverage: ${weatherResponse.clouds.all}% ", style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Horizontal Divider
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Sunrise and Sunset Times
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text("Sunrise: ${formatTime(weatherResponse.sys.sunrise)}", style = MaterialTheme.typography.bodyMedium)
                Text("Sunset: ${formatTime(weatherResponse.sys.sunset)}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// Helper function to format timestamps
private fun formatTime(unixTime: Long): String {
    val date = Date(unixTime * 1000) // Convert to milliseconds
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Set desired format
    return sdf.format(date)
}
