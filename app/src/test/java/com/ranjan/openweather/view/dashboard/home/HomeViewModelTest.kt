package com.ranjan.openweather.view.dashboard.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ranjan.openweather.DummyLocationFetcher
import com.ranjan.openweather.common.ResponseHelper
import com.ranjan.openweather.data.database.entities.Weather
import com.ranjan.openweather.domain.repository.WeatherRepository
import com.ranjan.openweather.view.dashboard.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = newSingleThreadContext("Dashboard Thread")

    @Mock
    private lateinit var weatherRepo: WeatherRepository

    @Mock
    private lateinit var weather: Weather

    @Before
    fun beforeSetup() {
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(weatherRepo)
    }

    @After
    fun tearUp() {
        Dispatchers.resetMain()
        dispatcher.close()
    }

    @Test
    fun `lat and long calls the getweatherinfo method`() {
        val lat = DummyLocationFetcher.getLatitude()
        val long = DummyLocationFetcher.getLongitude()
        viewModel.getWeatherData(lat, long)
        runBlocking(dispatcher) {
            Mockito.verify(weatherRepo, Mockito.times(1)).getWeatherInformation(lat, long)
        }
    }

    @Test
    fun `lat and long calls returns response `() {
        val response = flow<ResponseHelper<Weather>> {
            emit(ResponseHelper.Success(weather, 200))
        }
        val lat = DummyLocationFetcher.getLatitude()
        val long = DummyLocationFetcher.getLongitude()
        Mockito.`when`(weatherRepo.getWeatherInformation(lat, long)).thenReturn(response)

        viewModel.getWeatherData(lat, long)

        runBlocking(dispatcher) {
            Assert.assertEquals(weather, viewModel.weatherResponse.value.data)
        }
    }


}