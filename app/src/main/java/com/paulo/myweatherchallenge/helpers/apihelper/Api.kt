package com.paulo.myweatherchallenge.helpers.apihelper

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paulo.myweatherchallenge.BuildConfig
import com.paulo.myweatherchallenge.model.weather.WeatherDetail
import com.paulo.myweatherchallenge.model.weather.WeatherGroupResponse
import com.paulo.myweatherchallenge.model.weather.WeatherResponse
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE

            client.addInterceptor(interceptor)
                .build()

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

    @GET("$API_VERSION/weather?units=metric")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double?,
        @Query("lon") long: Double?,
        @Query(PATH_API_ID) appid: String
    ): WeatherResponse

    @GET("$API_VERSION/group?id=2267057,3117735,2988507,2950159,2618425,3169070,6058560,2964574,3067696,2761369&units=metric")
    suspend fun getWeatherByGroup(@Query(PATH_API_ID) appid: String): WeatherGroupResponse

    @GET("$API_VERSION/onecall?exclude=hourly,daily,minutely?lang=pt_br")
    suspend fun fetchDetail(
        @Query("lat") lat: Double?,
        @Query("lon") long: Double?,
        @Query(PATH_API_ID) appid: String
    ): WeatherDetail
}