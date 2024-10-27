package com.serdicagrid.serdicaweatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.serdicagrid.serdicaweatherapp.ui.theme.SerdicaWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SerdicaWeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GoogleMapScreen()
                }
            }
        }
    }

    @Composable
    fun GoogleMapScreen() {
        val context = LocalContext.current
        var userLocation by remember { mutableStateOf<LatLng?>(null) }

        // Initialize LocationManager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Permission Request Launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                requestLocationUpdates(locationManager) { location ->
                    userLocation = location
                }
            } else {
                Toast.makeText(context, "Location permission is required to show your location on the map", Toast.LENGTH_SHORT).show()
            }
        }

        // Check permission and request updates
        LaunchedEffect(Unit) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates(locationManager) { location ->
                    userLocation = location
                }
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        // Display Google Map
        GoogleMap(modifier = Modifier.fillMaxSize()) {
            userLocation?.let { location ->
                Marker(
                    position = location,
                    title = "Your Location"
                )
            }
        }
    }



    // Helper function to request location updates
    private fun requestLocationUpdates(
        locationManager: LocationManager,
        onLocationUpdated: (LatLng) -> Unit
    ) {
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                onLocationUpdated(LatLng(location.latitude, location.longitude))
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {
                Toast.makeText(this@MainActivity, "Location provider disabled", Toast.LENGTH_SHORT).show()
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,    // Update every 1000 milliseconds (1 second)
                10f,      // Update if location changes by 10 meters
                locationListener
            )
        }
    }
}
