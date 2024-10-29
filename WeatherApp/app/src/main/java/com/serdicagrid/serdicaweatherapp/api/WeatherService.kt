package com.serdicagrid.serdicaweatherapp.api

import com.serdicagrid.serdicaweatherapp.BuildConfig
import com.serdicagrid.serdicaweatherapp.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherService {

    private val apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY

    /**
     * Fetches current weather data for a specific latitude and longitude.
     * Returns a [WeatherData] object if successful, or null if an error occurs.
     */
    suspend fun fetchCurrentWeather(lat: Double, lon: Double): WeatherData? {
        val urlString = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        var connection: HttpsURLConnection? = null
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpsURLConnection
                connection.apply {
                    requestMethod = "GET"
                    connectTimeout = 5000
                    readTimeout = 5000
                }
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    parseCurrentWeatherData(response)
                } else {
                    println("Error: ${connection.responseCode} - ${connection.responseMessage}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                connection?.disconnect()
            }
        }
    }

    /**
     * Parses JSON response from the current weather endpoint.
     * @param data JSON string from the API response.
     * @return A [WeatherData] object with parsed weather information.
     */
    private fun parseCurrentWeatherData(data: String): WeatherData? {
        return try {
            val jsonObject = JSONObject(data)
            val main = jsonObject.getJSONObject("main")
            val weather = jsonObject.getJSONArray("weather").getJSONObject(0)

            WeatherData(
                temperature = main.getDouble("temp"),
                humidity = main.getInt("humidity"),
                condition = weather.getString("main"),
                timestamp = jsonObject.getLong("dt")
            )
        } catch (e: Exception) {
            println("Error parsing JSON: ${e.message}")
            null
        }
    }
}
