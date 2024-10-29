package com.serdicagrid.serdicaweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.model.WeatherData


@Composable
fun ForecastScreen(repository: WeatherRepository, modifier: Modifier = Modifier) {
    var weatherUIState by remember { mutableStateOf<WeatherUIState>(WeatherUIState.Loading) }

    LaunchedEffect(Unit) {
        weatherUIState = try {
            repository.getCurrentWeather(lat = 42.6977, lon = 23.3219)?.let {
                WeatherUIState.Success(it)
            } ?: WeatherUIState.Error("Failed to fetch weather data")
        } catch (e: Exception) {
            WeatherUIState.Error("An error occurred: ${e.localizedMessage}")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Forecast Screen", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        when (weatherUIState) {
            is WeatherUIState.Loading -> Text("Loading weather data...", style = MaterialTheme.typography.bodyMedium)
            is WeatherUIState.Error -> Text(
                text = (weatherUIState as WeatherUIState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            is WeatherUIState.Success -> WeatherInfo(weatherData = (weatherUIState as WeatherUIState.Success).weatherData)
        }
    }
}

@Composable
fun WeatherInfo(weatherData: WeatherData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Temperature: ${weatherData.temperature}°C", style = MaterialTheme.typography.bodyLarge)
        Text("Humidity: ${weatherData.humidity}%", style = MaterialTheme.typography.bodyLarge)
        Text("Condition: ${weatherData.condition}", style = MaterialTheme.typography.bodyLarge)
    }
}
