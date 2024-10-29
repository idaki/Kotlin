package com.serdicagrid.serdicaweatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.model.WeatherData

@Composable
fun ForecastScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // Ensure this call to load data is made when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.loadCurrentWeather(lat = 42.6977, lon = 23.3219) // Example coordinates
    }

    // Collect current weather data from StateFlow
    val weatherData: WeatherData? = viewModel.currentWeatherState.collectAsState(initial = null).value

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forecast Screen", style = MaterialTheme.typography.headlineSmall)

        // Display the weather data if available, otherwise show loading message
        weatherData?.let {
            Text(text = "Temperature: ${it.temperature}°C")
            Text(text = "Humidity: ${it.humidity}%")
            Text(text = "Condition: ${it.condition}")
        } ?: Text("Loading weather data...")
    }
}
