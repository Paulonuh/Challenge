package com.paulo.myweatherchallenge.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paulo.myweatherchallenge.base.BaseViewModel
import com.paulo.myweatherchallenge.model.weather.WeatherDetail
import com.paulo.myweatherchallenge.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Paulo Henrique Teixeira.
 */

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val mWeatherRepository: WeatherRepository
) : BaseViewModel() {
    val ldDetail: MutableLiveData<WeatherDetail> = MutableLiveData()

    fun fetchDetail(lat: Double?, lon: Double?) {
        defaultLaunch {
            val response = mWeatherRepository.fetchDetail(lat, lon)
            ldDetail.postValue(response)
        }
    }
}