package com.paulo.myweatherchallenge.model.weather


/**
 * Created by Paulo Henrique Teixeira.
 */

data class WeatherDetail(
    var alerts: List<Alerts>?,
    var current: Current?,
    var lat: Double?,
    var lon: Double?,
    var timezone: String?,
    var timezone_offset: Int?
)