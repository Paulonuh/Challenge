package com.paulo.myweatherchallenge.helpers.apihelper

import android.content.Context
import com.paulo.myweatherchallenge.BuildConfig
import com.paulo.myweatherchallenge.model.weather.WeatherResponseBody
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Created by Paulo Henrique Teixeira.
 */

class ApiHelper @Inject constructor() {
    private var mApi: Api = Api.Factory.create()

    suspend fun getWeatherByLocation(lat: Double?, lon: Double?, q: String?): WeatherResponseBody {
        return mApi.getWeatherByLocation(lat, lon, q, BuildConfig.TOKEN)
    }
}