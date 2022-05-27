package com.paulo.myweatherchallenge.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Paulo Henrique Teixeira.
 */

@Parcelize
data class Main(
    var feels_like: Double?,
    var humidity: Int?,
    var pressure: Int?,
    var temp: Double?,
    var temp_max: Double?,
    var temp_min: Double?
): Parcelable