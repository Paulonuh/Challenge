package com.paulo.myweatherchallenge.helpers.apihelper

import com.paulo.myweatherchallenge.BuildConfig
import com.paulo.myweatherchallenge.model.weather.WeatherDetail
import com.paulo.myweatherchallenge.model.weather.WeatherGroupResponse
import com.paulo.myweatherchallenge.model.weather.WeatherResponse
import javax.inject.Inject


/**
 * Created by Paulo Henrique Teixeira.
 */

class ApiHelper @Inject constructor() {
    private var mApi: Api = Api.Factory.create()

    suspend fun getWeatherByLocation(lat: Double?, lon: Double?): WeatherResponse {
        return mApi.getWeatherByLocation(lat, lon, BuildConfig.TOKEN)
    }


    suspend fun getWeatherByGroup(): WeatherGroupResponse {
        return mApi.getWeatherByGroup(BuildConfig.TOKEN)
    }

    suspend fun fetchDetail(lat: Double?, lon: Double?): WeatherDetail {
        return mApi.fetchDetail(lat, lon, BuildConfig.TOKEN)
    }
}