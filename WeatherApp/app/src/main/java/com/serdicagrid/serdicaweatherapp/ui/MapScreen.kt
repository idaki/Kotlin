import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    var userLocation by remember { locationService.locationState }
    val isLocationEnabled by remember { mutableStateOf(locationService.isLocationEnabled()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(0.0, 0.0), 10f, 0f, 0f)
    }

    LaunchedEffect(Unit) {
        if (!isLocationEnabled) {
            Toast.makeText(context, "Please enable location services to use the map", Toast.LENGTH_LONG).show()
        } else if (locationService.hasLocationPermission()) {
            locationService.requestLocationUpdates { location ->
                userLocation = location
                cameraPositionState.position = CameraPosition(location, 15f, 0f, 0f)
            }
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
