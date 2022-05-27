package com.paulo.myweatherchallenge.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Paulo Henrique Teixeira.
 */

@Parcelize
data class Sys(
    var country: String?,
    var id: Int?,
    var message: Double?,
    var sunrise: Int?,
    var sunset: Int?,
    var type: Int?,
    var timezone: Int?
):Parcelable