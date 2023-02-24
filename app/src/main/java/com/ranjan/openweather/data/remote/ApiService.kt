package com.ranjan.openweather.data.remote

import com.ranjan.openweather.BuildConfig
import com.ranjan.openweather.data.remote.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?units=metric")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String = BuildConfig.API_KEY
    ): Response<ResponseDTO>
}