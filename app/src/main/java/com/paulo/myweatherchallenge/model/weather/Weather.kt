package com.paulo.myweatherchallenge.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Paulo Henrique Teixeira.
 */

@Parcelize
data class Weather(
    var description: String?,
    var icon: String?,
    var id: Int?,
    var main: String?
):Parcelable