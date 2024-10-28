package com.serdicagrid.serdicaweatherapp.ui

import ClothingRecommendationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SerdicaWeatherAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var selectedScreen by remember { mutableStateOf(Screen.Forecast) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedScreen) { screen ->
                selectedScreen = screen
            }
        }
    ) { paddingValues ->
        when (selectedScreen) {
            Screen.Forecast -> ForecastScreen(viewModel, Modifier.padding(paddingValues))
            Screen.Map -> MapScreen(Modifier.padding(paddingValues))
            Screen.Clothing -> ClothingRecommendationScreen(viewModel, Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun BottomNavigationBar(selectedScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Cloud, contentDescription = "Forecast") },
            label = { Text("Forecast") },
            selected = selectedScreen == Screen.Forecast,
            onClick = { onScreenSelected(Screen.Forecast) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") },
            selected = selectedScreen == Screen.Map,
            onClick = { onScreenSelected(Screen.Map) }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Checkroom, contentDescription = "Clothing") },
            label = { Text("Clothing") },
            selected = selectedScreen == Screen.Clothing,
            onClick = { onScreenSelected(Screen.Clothing) }
        )
    }
}

enum class Screen {
    Forecast, Map, Clothing
}
