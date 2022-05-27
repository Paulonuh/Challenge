package com.paulo.myweatherchallenge.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Paulo Henrique Teixeira.
 */

@Parcelize
data class Clouds(
    var all: Int?
):Parcelable