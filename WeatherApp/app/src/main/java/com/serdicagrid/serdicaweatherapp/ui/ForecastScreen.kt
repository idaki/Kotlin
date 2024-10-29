import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.serdicagrid.serdicaweatherapp.data.WeatherRepository
import com.serdicagrid.serdicaweatherapp.model.WeatherData

@Composable
fun ForecastScreen(repository: WeatherRepository, modifier: Modifier = Modifier) {
    var weatherData by remember { mutableStateOf<WeatherData?>(null) }

    LaunchedEffect(Unit) {
        weatherData = repository.getCurrentWeather(lat = 42.6977, lon = 23.3219)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forecast Screen", style = MaterialTheme.typography.headlineSmall)
        weatherData?.let {
            Text("Temperature: ${it.temperature}°C")
            Text("Humidity: ${it.humidity}%")
            Text("Condition: ${it.condition}")
        } ?: Text("Loading weather data...")
    }
}
