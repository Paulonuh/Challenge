package com.paulo.myweatherchallenge.model.weather


/**
 * Created by Paulo Henrique Teixeira.
 */

data class Sys(
    var country: String?,
    var id: Int?,
    var message: Double?,
    var sunrise: Int?,
    var sunset: Int?,
    var type: Int?,
    var timezone: Int?
)