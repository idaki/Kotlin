package com.serdicagrid.serdicaweatherapp.api

import com.serdicagrid.serdicaweatherapp.BuildConfig
import com.serdicagrid.serdicaweatherapp.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class WeatherService {

    private val apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
    private val client = OkHttpClient()

    suspend fun fetchCurrentWeather(lat: Double, lon: Double, retries: Int = 3): WeatherData? {
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"

        return withContext(Dispatchers.IO) {
            repeat(retries) { attempt ->
                try {
                    val request = Request.Builder().url(url).get().build()
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            response.body?.string()?.let { responseBody ->
                                return@withContext parseCurrentWeatherData(responseBody)
                            }
                        } else {
                            println("Request failed with status: ${response.code}")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    if (attempt == retries - 1) {
                        return@withContext null
                    }
                }
            }
            null // Returns null if all retries fail
        }
    }

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
            e.printStackTrace()
            null
        }
    }
}
