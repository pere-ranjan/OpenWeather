package com.ranjan.openweather.domain.repository

import com.ranjan.openweather.common.ResponseHelper
import com.ranjan.openweather.data.database.entities.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherInformation(latitude: Double, longitude: Double): Flow<ResponseHelper<Weather>>

    fun getPreviousWeatherInformation(): Flow<List<Weather>>
}