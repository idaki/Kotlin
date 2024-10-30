package com.serdicagrid.serdicaweatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.serdicagrid.serdicaweatherapp.api.LocationService

@Composable
fun MapScreen(locationService: LocationService, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userLocation by locationService.locationState.collectAsState(initial = null) // Use collectAsState to collect the StateFlow
    val isLocationEnabled = locationService.isLocationEnabled() // Call this function directly

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(0.0, 0.0), 10f, 0f, 0f)
    }

    // Call requestLocationUpdates without any parameters
    LaunchedEffect(Unit) {
        if (!isLocationEnabled) {
            Toast.makeText(context, "Please enable location services to use the map", Toast.LENGTH_LONG).show()
        } else if (locationService.hasLocationPermission()) {
            locationService.requestLocationUpdates()
        } else {
            Toast.makeText(context, "Location permission is not granted. Please enable it.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLocationEnabled) {
            Text(text = "Your Location", style = MaterialTheme.typography.headlineSmall)

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                userLocation?.let { location ->
                    Marker(
                        state = MarkerState(position = location),
                        title = "Your Location"
                    )
                    cameraPositionState.position = CameraPosition(location, 15f, 0f, 0f)
                }
            }
        } else {
            Text(
                text = "Location services are disabled. Please enable them to view the map.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
