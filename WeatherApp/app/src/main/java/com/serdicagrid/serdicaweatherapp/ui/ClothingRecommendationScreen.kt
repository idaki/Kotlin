import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ClothingRecommendationScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    // Collect the weatherDataState from the ViewModel
    val weatherData by viewModel.weatherDataState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Clothing Recommendation", style = MaterialTheme.typography.headlineSmall)
        weatherData?.let {
            val recommendation = when {
                it.temperature > 25 -> "It's hot. Wear light clothing!"
                it.temperature < 10 -> "It's cold. Wear a jacket!"
                else -> "Weather is mild. Dress comfortably."
            }
            Text(text = recommendation)
        } ?: Text("Loading recommendation...")
    }
}
