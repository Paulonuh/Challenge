package com.paulo.myweatherchallenge.model.weather

/**
 * Created by Paulo Henrique Teixeira.
 */

data class WeatherGroupResponse(
    var cnt: Int?,
    var list: List<WeatherResponse>?
)