package com.ranjan.openweather.view.dashboard.history

import com.ranjan.openweather.data.database.entities.Weather
import com.ranjan.openweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest {

    private lateinit var viewModel: HistoryViewModel

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var weatherList: List<Weather>

    @Before
    fun setUp() {
        viewModel = HistoryViewModel(weatherRepository)
    }

    @Test
    fun isValueReturned() {
        val response = flow { emit(weatherList) }
        Mockito.`when`(viewModel.getPreviousWeatherData).thenReturn(response)

        runBlocking {
            Assert.assertEquals(weatherList, viewModel.getPreviousWeatherData.first())
        }
    }
}