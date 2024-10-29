package com.serdicagrid.serdicaweatherapp.ui

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.widget.Toast
import com.serdicagrid.serdicaweatherapp.api.LocationService
import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize permission launcher
        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(this, "Location permission is required for the app to function", Toast.LENGTH_LONG).show()
            }
        }

        val weatherService = WeatherService()
        val repository = WeatherRepository(weatherService)

        setContent {
            SerdicaWeatherAppTheme {
                MainScreen(repository)
            }
        }

        // Request location permission
        if (!LocationService(this).hasLocationPermission()) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
