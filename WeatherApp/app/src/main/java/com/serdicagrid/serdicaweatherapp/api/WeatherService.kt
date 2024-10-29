package com.serdicagrid.serdicaweatherapp.api

import com.serdicagrid.serdicaweatherapp.BuildConfig
import com.serdicagrid.serdicaweatherapp.model.WeatherData
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherService {

    private val apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY

    // Fetches current weather data
    fun fetchCurrentWeather(lat: Double, lon: Double): WeatherData? {
        val urlString = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        return getWeatherData(urlString)?.let { parseCurrentWeatherData(it) }
    }

    // Fetches forecast data
    fun fetchForecast(lat: Double, lon: Double): List<WeatherData>? {
        val urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        return getWeatherData(urlString)?.let { parseForecastData(it) }
    }

    // Makes HTTP request and retrieves data
    private fun getWeatherData(urlString: String): String? {
        return try {
            val url = URL(urlString)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 5000
            urlConnection.readTimeout = 5000

            val responseCode = urlConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                response.toString()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Parses current weather data JSON response
    private fun parseCurrentWeatherData(data: String): WeatherData {
        val json = JSONObject(data)
        val main = json.getJSONObject("main")
        val weatherArray = json.getJSONArray("weather")
        val weather = weatherArray.getJSONObject(0)

        return WeatherData(
            temperature = main.getDouble("temp"),
            humidity = main.getInt("humidity"),
            condition = weather.getString("main")
        )
    }

    // Parses forecast data JSON response
    private fun parseForecastData(data: String): List<WeatherData> {
        val json = JSONObject(data)
        val list = json.getJSONArray("list")

        val forecastList = mutableListOf<WeatherData>()
        for (i in 0 until list.length()) {
            val item = list.getJSONObject(i)
            val main = item.getJSONObject("main")
            val weatherArray = item.getJSONArray("weather")
            val weather = weatherArray.getJSONObject(0)

            forecastList.add(
                WeatherData(
                    temperature = main.getDouble("temp"),
                    humidity = main.getInt("humidity"),
                    condition = weather.getString("main"),
                    timestamp = item.getLong("dt")
                )
            )
        }

        return forecastList
    }
}
