package com.serdicagrid.serdicaweatherapp.ui


import MainScreen
import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.serdicagrid.serdicaweatherapp.api.LocationService
import com.serdicagrid.serdicaweatherapp.api.WeatherService
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var locationService: LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize LocationService
        locationService = LocationService(applicationContext)

        // Handle location permission request
        locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                locationService.requestLocationUpdates() // Start location updates if permission granted
            } else {
                Toast.makeText(this, "Location permission is required for app functionality", Toast.LENGTH_LONG).show()
            }
        }

        // Request permission if not granted
        if (!locationService.hasLocationPermission()) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            locationService.requestLocationUpdates() // Start updates if permission is already granted
        }

        // Initialize WeatherRepository and set up content
        val repository = WeatherRepository(WeatherService())
        setContent {
            SerdicaWeatherAppTheme {
                MainScreen(repository, locationService)
            }
        }
    }
}