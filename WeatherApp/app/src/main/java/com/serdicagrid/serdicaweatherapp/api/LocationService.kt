package com.serdicagrid.serdicaweatherapp.api
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng

class LocationService(private val context: Context) {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val locationState = mutableStateOf<LatLng?>(null)

    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Requests location updates and updates locationState with the new location
    fun requestLocationUpdates() {
        if (!hasLocationPermission()) {
            Toast.makeText(context, "Location permission is required", Toast.LENGTH_SHORT).show()
            return
        }

        val locationListener = LocationListener { location ->
            locationState.value = LatLng(location.latitude, location.longitude)
        }

        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000L, 10f, locationListener
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(context, "Location access denied.", Toast.LENGTH_SHORT).show()
        }
    }
}
