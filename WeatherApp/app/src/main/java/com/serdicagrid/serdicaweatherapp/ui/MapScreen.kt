package com.serdicagrid.serdicaweatherapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.serdicagrid.serdicaweatherapp.api.LocationService

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Initialize LocationService
    val locationService = remember { LocationService(context) }

    // Initialize camera position state with a default position, which will update once userLocation is available
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(0.0, 0.0), 10f, 0f, 0f) // Default position, zoom, tilt, and bearing
    }

    // Request location updates from GPS
    LaunchedEffect(Unit) {
        if (locationService.hasLocationPermission()) {
            locationService.requestLocationUpdates { location ->
                userLocation = location
                // Update camera position to focus on GPS location with zoom level of 15
                cameraPositionState.position = CameraPosition(location, 10f, 0f, 0f)
            }
        } else {
            Toast.makeText(context, "Location permission is not granted", Toast.LENGTH_SHORT).show()
        }
    }

    // Display the Google Map with the user's location marker
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Location", style = MaterialTheme.typography.headlineSmall)

        // Map with user's location marker and zoom functionality
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState // Set camera position state to follow user location
        ) {
            userLocation?.let { location ->
                Marker(
                    state = MarkerState(position = location),
                    title = "Your Location"
                )
            }
        }
    }
}
