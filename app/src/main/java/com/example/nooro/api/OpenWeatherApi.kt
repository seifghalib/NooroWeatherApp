package com.example.nooro.api

import com.example.nooro.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.nooro.data.WeatherResponse

interface OpenWeatherApi {

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val ICON_URL = "https://openweathermap.org/img/wn/%s@2x.png"
        const val OPEN_CLIENT_ID = BuildConfig.OPENWEATHER_API_KEY
        const val ZIP_CODE = "98052,us"
    }

//    @GET("weather?zip=$ZIP_CODE,us&appid=$OPEN_CLIENT_ID&units=imperial")
//    suspend fun getWeather() : Response<WeatherResponse>

//    follow this link to add intercept https://github.com/nameisjayant/Retrofit-Authorization-Token
    @GET("weather")
    suspend fun getWeather(
        @Query("zip") zipcode : String = ZIP_CODE,
        @Query("appid") appid : String = OPEN_CLIENT_ID,
        @Query("units") units : String = "imperial"
    ) : Response<WeatherResponse>
}