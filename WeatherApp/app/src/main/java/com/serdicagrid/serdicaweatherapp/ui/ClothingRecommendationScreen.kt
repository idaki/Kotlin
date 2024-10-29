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
fun ClothingRecommendationScreen(repository: WeatherRepository, modifier: Modifier = Modifier) {
    var weatherData by remember { mutableStateOf<WeatherData?>(null) }

    LaunchedEffect(Unit) {
        weatherData = repository.getCurrentWeather(lat = 42.6977, lon = 23.3219)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Clothing Recommendation", style = MaterialTheme.typography.headlineSmall)
        Text(
            text = weatherData?.let {
                when {
                    it.temperature > 25 -> "It's hot. Wear light clothing!"
                    it.temperature < 10 -> "It's cold. Wear a jacket!"
                    else -> "Weather is mild. Dress comfortably."
                }
            } ?: "Loading recommendation..."
        )
    }
}