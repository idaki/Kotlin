package com.serdicagrid.serdicaweatherapp.api

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng


class LocationService(private val context: Context) {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun hasLocationPermission() = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun isLocationEnabled() =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    fun requestLocationUpdates(onLocationUpdated: (LatLng) -> Unit) {
        if (!hasLocationPermission()) {
            Toast.makeText(context, "Location permission is required", Toast.LENGTH_SHORT).show()
            return
        }

        val locationListener = LocationListener { location ->
            onLocationUpdated(LatLng(location.latitude, location.longitude))
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000L, 10f, locationListener
        )
    }
}
