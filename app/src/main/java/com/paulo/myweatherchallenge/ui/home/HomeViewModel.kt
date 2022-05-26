package com.paulo.myweatherchallenge.ui.home

import androidx.lifecycle.MutableLiveData
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.base.BaseViewModel
import com.paulo.myweatherchallenge.model.weather.WeatherResponseBody
import com.paulo.myweatherchallenge.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Paulo Henrique Teixeira.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mWeatherRepository: WeatherRepository
) : BaseViewModel() {

    val ldWeather = MutableLiveData<WeatherResponseBody>()

    fun getWeather() {
        defaultLaunch(R.string.error_on_get_weather) {
            val responseBody = mWeatherRepository.getWeather(null, null, "LISBOA")
            ldWeather.postValue(responseBody)
        }
    }
}