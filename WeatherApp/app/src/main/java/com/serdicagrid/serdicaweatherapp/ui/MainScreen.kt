import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.ui.ForecastScreen
import com.serdicagrid.serdicaweatherapp.ui.MapScreen

@Composable
fun MainScreen(repository: WeatherRepository) {
    var selectedScreen by remember { mutableStateOf(Screen.Forecast) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedScreen) { selectedScreen = it }
        }
    ) { paddingValues ->
        when (selectedScreen) {
            Screen.Forecast -> ForecastScreen(repository, Modifier.padding(paddingValues))
            Screen.Map -> MapScreen(Modifier.padding(paddingValues))
            Screen.Clothing -> ClothingRecommendationScreen(repository, Modifier.padding(paddingValues))
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
