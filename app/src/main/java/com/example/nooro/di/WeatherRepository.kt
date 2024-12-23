package com.example.nooro.di

import com.example.nooro.api.OpenWeatherApi
import com.example.nooro.utils.toResultFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val openWeatherApi: OpenWeatherApi){

    fun getWeather() = toResultFlow {
        openWeatherApi.getWeather()
    }
}