package com.ranjan.openweather.view.dashboard.history

import androidx.lifecycle.ViewModel
import com.ranjan.openweather.data.database.entities.Weather
import com.ranjan.openweather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(private val weatherRepo: WeatherRepository) :
    ViewModel() {

    val getPreviousWeatherData: Flow<List<Weather>> get() = weatherRepo.getPreviousWeatherInformation()

}