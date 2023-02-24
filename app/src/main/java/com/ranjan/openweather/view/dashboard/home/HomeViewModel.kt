package com.ranjan.openweather.view.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranjan.openweather.common.ResponseHelper
import com.ranjan.openweather.data.database.entities.Weather
import com.ranjan.openweather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherResponse =
        MutableStateFlow<ResponseHelper<Weather>>(ResponseHelper.Loading())
    val weatherResponse get() = _weatherResponse.asStateFlow()
    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.getWeatherInformation(latitude, longitude).collect { response ->
                _weatherResponse.value = response
            }
        }
    }
}