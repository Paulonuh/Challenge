package com.paulo.myweatherchallenge.model.weather


/**
 * Created by Paulo Henrique Teixeira.
 */

data class Alerts(
    var description: String?,
    var end: Int?,
    var event: String?,
    var sender_name: String?,
    var start: Int?,
    var tags: List<String>?
)