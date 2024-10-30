import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.api.LocationService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.model.WeatherResponse

@Composable
fun MainScreen(repository: WeatherRepository, locationService: LocationService) {
    var selectedScreen by remember { mutableStateOf(Screen.Forecast) }
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(true) } // Track loading state

    // Collect location updates
    val locationState by locationService.locationState.collectAsState(initial = null)

    // Fetch weather data when location is available
    LaunchedEffect(locationState) {
        locationState?.let { loc ->
            loading = true // Set loading to true before fetching
            try {
                weatherData = repository.getCurrentWeather(lat = loc.latitude, lon = loc.longitude)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to fetch weather data: ${e.localizedMessage}"
                weatherData = null // Reset weather data on error
            } finally {
                loading = false // Set loading to false after fetch attempt
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedScreen) { selectedScreen = it } }
    ) { paddingValues ->
        when (selectedScreen) {
            Screen.Forecast -> {
                if (loading) {
                    // Use Box to center the loading indicator
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator() // Centered loading indicator
                    }
                } else {
                    ForecastContent(weatherData, errorMessage, Modifier.padding(paddingValues))
                }
            }
            Screen.Map -> MapScreen(locationService, Modifier.padding(paddingValues))
            Screen.Clothing -> ClothingRecommendationScreen(weatherData, Modifier.padding(paddingValues))
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
