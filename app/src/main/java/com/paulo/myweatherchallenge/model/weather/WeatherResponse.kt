package com.paulo.myweatherchallenge.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Paulo Henrique Teixeira.
 */

@Parcelize
data class WeatherResponse(
    var base: String?,
    var clouds: Clouds?,
    var cod: Int?,
    var coord: Coord?,
    var dt: Int?,
    var id: Int?,
    var main: Main?,
    var name: String?,
    var sys: Sys?,
    var timezone: Int?,
    var visibility: Int?,
    var weather: List<Weather>?,
    var wind: Wind?
) : Parcelable