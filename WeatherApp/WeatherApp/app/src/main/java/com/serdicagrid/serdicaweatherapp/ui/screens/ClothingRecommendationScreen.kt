package com.serdicagrid.serdicaweatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serdicagrid.serdicaweatherapp.model.WeatherResponse

@Composable
fun ClothingRecommendationScreen(weatherResponse: WeatherResponse?, modifier: Modifier = Modifier) {
    var loading by remember { mutableStateOf(weatherResponse == null) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Clothing Recommendation",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Show loading indicator or recommendation
        if (loading) {
            CircularProgressIndicator()
        } else {
            weatherResponse?.let { response ->
                // Display the city name
                Text(
                    text = "City: ${response.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp) // Add some space below the city name
                )

                // Display clothing recommendation
                Text(
                    text = getClothingRecommendation(response),
                    style = MaterialTheme.typography.bodyMedium
                )
            } ?: run {
                Text(
                    text = "No weather data available.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Function to determine clothing recommendation based on temperature and weather conditions
fun getClothingRecommendation(response: WeatherResponse): String {
    val temperature = response.main.temp
    val weatherCondition = response.weather.firstOrNull()?.description ?: "Unknown"

    return when {
        temperature > 25 -> "🌞 It's hot! Wear light clothing to stay cool."
        temperature in 10.0..25.0 -> "🌤️ It's warm! A t-shirt and shorts should be fine."
        temperature < 10 -> "🧥 It's cold! Make sure to wear a jacket!"
        weatherCondition.contains("rain", ignoreCase = true) -> "🌧️ It's rainy! Don't forget your umbrella!"
        weatherCondition.contains("snow", ignoreCase = true) -> "❄️ It's snowing! Wear a warm coat and boots!"
        weatherCondition.contains("fog", ignoreCase = true) -> "🌫️ It's foggy! Drive safely and wear warm layers!"
        weatherCondition.contains("cloud", ignoreCase = true) -> "☁️ It's cloudy! Dress comfortably and keep a light jacket handy."
        else -> "🌈 Weather is mild. Dress comfortably."
    }
}
