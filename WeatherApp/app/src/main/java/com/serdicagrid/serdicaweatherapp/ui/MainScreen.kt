package com.serdicagrid.serdicaweatherapp.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

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
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Cloud, contentDescription = "Forecast") },
            label = { Text("Forecast") },
            selected = selectedScreen == Screen.Forecast,
            onClick = { onScreenSelected(Screen.Forecast) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") },
            selected = selectedScreen == Screen.Map,
            onClick = { onScreenSelected(Screen.Map) }
        )
        NavigationBarItem(
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