package com.serdicagrid.serdicaweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.model.WeatherData

@Composable
fun ForecastScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // Collect weatherDataState from the ViewModel
    val weatherData: WeatherData? by viewModel.weatherDataState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forecast Screen", style = MaterialTheme.typography.headlineSmall)
        weatherData?.let {
            Text(text = "Temperature: ${it.temperature}°C")
            Text(text = "Humidity: ${it.humidity}%")
            Text(text = "Condition: ${it.condition}")
        } ?: Text("Loading weather data...")
    }
}