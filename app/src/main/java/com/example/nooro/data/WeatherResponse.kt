package com.example.nooro.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
) : Parcelable {

    @Parcelize
    data class Weather(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    ) : Parcelable

    @Parcelize
    data class Main (
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Long,
        val humidity: Long
    ) : Parcelable
}
