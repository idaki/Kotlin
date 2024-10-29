import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serdicagrid.serdicaweatherapp.api.LocationService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.model.WeatherData



@Composable
fun MainScreen(repository: WeatherRepository, locationService: LocationService) {
    var selectedScreen by remember { mutableStateOf(Screen.Forecast) }
    var weatherData by remember { mutableStateOf<WeatherData?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val location = locationService.locationState.value

    // Fetch weather data when location is available
    LaunchedEffect(location) {
        location?.let {
            try {
                weatherData = repository.getCurrentWeather(lat = it.latitude, lon = it.longitude)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to fetch weather data: ${e.localizedMessage}"
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen) { selectedScreen = it } }
    ) { paddingValues ->
        when (selectedScreen) {
            Screen.Forecast -> ForecastContent(weatherData, errorMessage, Modifier.padding(paddingValues))
            Screen.Map -> MapScreen(locationService, Modifier.padding(paddingValues))
            Screen.Clothing -> ClothingRecommendationScreen(repository, Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun ForecastContent(weatherData: WeatherData?, errorMessage: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (weatherData != null) {
            Text("Temperature: ${weatherData.temperature}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Humidity: ${weatherData.humidity}%", style = MaterialTheme.typography.bodyLarge)
            Text("Condition: ${weatherData.condition}", style = MaterialTheme.typography.bodyLarge)
        } else if (errorMessage != null) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
        } else {
            Text("Loading weather data...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun BottomNavigationBar(selectedScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    val screens = listOf(
        Screen.Forecast to Icons.Default.Cloud,
        Screen.Map to Icons.Default.Map,
        Screen.Clothing to Icons.Default.Checkroom
    )

    NavigationBar {
        screens.forEach { (screen, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = screen.name) },
                label = { Text(screen.name) },
                selected = selectedScreen == screen,
                onClick = { onScreenSelected(screen) }
            )
        }
    }
}

enum class Screen { Forecast, Map, Clothing }