import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.serdicagrid.serdicaweatherapp.model.WeatherData

class MainViewModel : ViewModel() {
    private val _weatherDataState = MutableStateFlow<WeatherData?>(null)
    val weatherDataState: StateFlow<WeatherData?> = _weatherDataState

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            // Simulate fetching data with correct types
            _weatherDataState.value = WeatherData(temperature = 20.0, humidity = 65, condition = "Sunny")
        }
    }
}
