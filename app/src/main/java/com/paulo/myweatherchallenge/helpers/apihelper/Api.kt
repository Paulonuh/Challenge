package com.paulo.myweatherchallenge.helpers.apihelper

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paulo.myweatherchallenge.BuildConfig
import com.paulo.myweatherchallenge.model.weather.WeatherResponseBody
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Paulo Henrique Teixeira.
 */

interface Api {
    object Factory {
        // retrofit method
        fun create(): Api {
            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

            val build = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .client(client.build())
                .build()

            return build.create(Api::class.java)
        }
    }


    companion object {
        const val API_VERSION = "2.5"
        const val PATH_API_ID = "appid"
    }

    @GET("$API_VERSION/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double?,
        @Query("lon") long: Double?,
        @Query("q") q: String?,
        @Path(PATH_API_ID) appid: String
    ): WeatherResponseBody
}