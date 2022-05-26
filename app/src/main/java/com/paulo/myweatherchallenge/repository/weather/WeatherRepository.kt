package com.paulo.myweatherchallenge.repository.weather

import com.paulo.myweatherchallenge.helpers.apihelper.ApiHelper
import com.paulo.myweatherchallenge.model.weather.WeatherResponseBody
import javax.inject.Inject


/**
 * Created by Paulo Henrique Teixeira.
 */

class WeatherRepository @Inject constructor(
    private val mApiHelper: ApiHelper,
) {

    suspend fun getWeather(lat: Double?, lon: Double?, q: String?): WeatherResponseBody {
        return mApiHelper.getWeatherByLocation(lat, lon, q)
    }
}