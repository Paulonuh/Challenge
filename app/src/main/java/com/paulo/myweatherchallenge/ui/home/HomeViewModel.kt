package com.paulo.myweatherchallenge.ui.home

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.base.BaseViewModel
import com.paulo.myweatherchallenge.helpers.gps.GpsHelper
import com.paulo.myweatherchallenge.model.exception.LocationNotFoundException
import com.paulo.myweatherchallenge.model.weather.WeatherGroupResponse
import com.paulo.myweatherchallenge.model.weather.WeatherResponse
import com.paulo.myweatherchallenge.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Paulo Henrique Teixeira.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mWeatherRepository: WeatherRepository,
    private val gpsHelper: GpsHelper
) : BaseViewModel() {

    val ldMyLocationLoading = MutableLiveData<Boolean>()
    val ldWeatherLocation = MutableLiveData<WeatherResponse>()
    val ldWeatherGroup = MutableLiveData<WeatherGroupResponse>()

    val alertGps: MutableLiveData<ResolvableApiException> = MutableLiveData()
    val locationNotFound: MutableLiveData<Unit> = MutableLiveData()

    fun fetchLocation() {
        gpsHelper.fetchLastLocation(onLoadingChangeListener = { isLoading ->
            ldMyLocationLoading.postValue(isLoading)
        },
            onErrorListener = { exception ->
                when (exception) {
                    is ResolvableApiException -> {
                        alertGps.postValue(exception)
                    }
                    is LocationNotFoundException -> {
                        locationNotFound.postValue(Unit)
                    }
                    else -> {
                        //TODO implement a handlerException
                    }
                }
            },
            onSuccessListener = { location ->
                getWeather(location)
            }
        )
    }

    private fun getWeather(location: Location) {
        ldMyLocationLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val responseBody = mWeatherRepository.getWeather(location.latitude, location.longitude)
                    ldWeatherLocation.postValue(responseBody)
                    ldMyLocationLoading.postValue(false)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    ldMyLocationLoading.postValue(false)
                }
            }
        }
    }

    fun getWeatherGroup() {
        defaultLaunch(R.string.error_on_get_weather) {
            withContext(Dispatchers.IO) {
                val responseBody = mWeatherRepository.getWeatherGroup()
                ldWeatherGroup.postValue(responseBody)
            }
        }
    }
}