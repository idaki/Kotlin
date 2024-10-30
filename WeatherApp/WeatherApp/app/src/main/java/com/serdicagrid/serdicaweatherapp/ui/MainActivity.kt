package com.serdicagrid.serdicaweatherapp.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.serdicagrid.serdicaweatherapp.api.LocationService
import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.ui.screens.MainScreen
import com.serdicagrid.serdicaweatherapp.ui.screens.WelcomeScreen

import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var locationService: LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationService = LocationService(applicationContext)
        setupLocationPermission()

        setContent {
            SerdicaWeatherAppTheme {
                var showWelcomeScreen by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000)
                    showWelcomeScreen = false
                }

                if (showWelcomeScreen) {
                    WelcomeScreen()
                } else {
                    val repository = WeatherRepository(WeatherService())
                    MainScreen(repository, locationService)
                }
            }
        }
    }

    private fun setupLocationPermission() {
        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) locationService.requestLocationUpdates()
            else Toast.makeText(this, "Location permission is required for app functionality", Toast.LENGTH_LONG).show()
        }
        if (!locationService.hasLocationPermission()) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            locationService.requestLocationUpdates()
        }
    }
}
