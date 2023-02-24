package com.ranjan.openweather.data

import com.ranjan.openweather.common.NetworkUtils
import com.ranjan.openweather.common.ResponseHelper
import com.ranjan.openweather.common.safeApiCall
import com.ranjan.openweather.data.database.WeatherDao
import com.ranjan.openweather.data.remote.ApiService
import com.ranjan.openweather.data.remote.dto.toWeather
import com.ranjan.openweather.data.database.entities.Weather
import com.ranjan.openweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val weatherDao: WeatherDao,
    private val networkUtils: NetworkUtils
) : WeatherRepository {

    override fun getWeatherInformation(
        latitude: Double, longitude: Double
    ): Flow<ResponseHelper<Weather>> =
        safeApiCall(networkUtils) { apiService.getWeatherData(latitude, longitude) }.onEach {
            if (it is ResponseHelper.Success) weatherDao.saveData(it.data?.toWeather())
        }.map {
            return@map when (it) {
                is ResponseHelper.Success -> ResponseHelper.Success(
                    it.data?.toWeather()!!, it.responseCode
                )
                is ResponseHelper.Loading -> ResponseHelper.Loading()
                else -> ResponseHelper.Error(it.message, it.responseCode)
            }
        }

    override fun getPreviousWeatherInformation(): Flow<List<Weather>> =
        weatherDao.getSavedWeatherInformation()


}