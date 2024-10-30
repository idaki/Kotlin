package com.serdicagrid.serdicaweatherapp.api

import com.serdicagrid.serdicaweatherapp.BuildConfig
import com.serdicagrid.serdicaweatherapp.model.Clouds
import com.serdicagrid.serdicaweatherapp.model.Coordinates
import com.serdicagrid.serdicaweatherapp.model.MainWeather
import com.serdicagrid.serdicaweatherapp.model.SystemInfo
import com.serdicagrid.serdicaweatherapp.model.WeatherCondition
import com.serdicagrid.serdicaweatherapp.model.WeatherResponse
import com.serdicagrid.serdicaweatherapp.model.Wind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class WeatherService {

    private val apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
    private val client = OkHttpClient()

    suspend fun fetchCurrentWeather(lat: Double, lon: Double, retries: Int = 3): WeatherResponse? {
        val url = buildUrl(lat, lon)

        return withContext(Dispatchers.IO) {
            repeat(retries) { attempt ->
                try {
                    val response = executeRequest(url)
                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            return@withContext parseWeatherResponse(responseBody)
                        }
                    } else {
                        println("Request failed with status: ${response.code}")
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

    private fun buildUrl(lat: Double, lon: Double): String {
        return "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
    }

    private fun executeRequest(url: String) = client.newCall(Request.Builder().url(url).get().build()).execute()

    private fun parseWeatherResponse(data: String): WeatherResponse? {
        return try {
            val jsonObject = JSONObject(data)

            val coord = jsonObject.getJSONObject("coord")
            val weatherArray = jsonObject.getJSONArray("weather")
            val main = jsonObject.getJSONObject("main")
            val wind = jsonObject.getJSONObject("wind")
            val clouds = jsonObject.getJSONObject("clouds")
            val sys = jsonObject.getJSONObject("sys")

            WeatherResponse(
                coord = Coordinates(
                    lon = coord.getDouble("lon"),
                    lat = coord.getDouble("lat")
                ),
                weather = List(weatherArray.length()) { index ->
                    val weatherObject = weatherArray.getJSONObject(index)
                    WeatherCondition(
                        id = weatherObject.getInt("id"),
                        main = weatherObject.getString("main"),
                        description = weatherObject.getString("description"),
                        icon = weatherObject.getString("icon")
                    )
                },
                main = MainWeather(
                    temp = main.getDouble("temp"),
                    feelsLike = main.getDouble("feels_like"),
                    tempMin = main.getDouble("temp_min"),
                    tempMax = main.getDouble("temp_max"),
                    pressure = main.getInt("pressure"),
                    humidity = main.getInt("humidity")
                ),
                visibility = jsonObject.getInt("visibility"),
                wind = Wind(
                    speed = wind.getDouble("speed"),
                    deg = wind.getInt("deg")
                ),
                clouds = Clouds(
                    all = clouds.getInt("all")
                ),
                dt = jsonObject.getLong("dt"),
                timezone = jsonObject.getString("timezone"),
                name = jsonObject.getString("name"),
                sys = SystemInfo(
                    sunrise = sys.getLong("sunrise"),
                    sunset = sys.getLong("sunset")
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}