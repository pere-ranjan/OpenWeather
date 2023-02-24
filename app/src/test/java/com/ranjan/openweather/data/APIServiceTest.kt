package com.ranjan.openweather.data

import com.ranjan.openweather.DummyLocationFetcher
import com.ranjan.openweather.ReadingHelper
import com.ranjan.openweather.data.remote.ApiService
import com.ranjan.openweather.data.remote.dto.ResponseDTO
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIServiceTest {

    private lateinit var apiService: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var dummyResponseDTO: ResponseDTO

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testApi() {
        runTest {
            val mockResponse = MockResponse()
            val content = ReadingHelper.readFileResource("/success_response.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)

            val response = apiService.getWeatherData(
                DummyLocationFetcher.getLatitude(), DummyLocationFetcher.getLongitude()
            )
            mockWebServer.takeRequest()
            Assert.assertEquals(true, response.body() != null)
            Assert.assertEquals(true, response.isSuccessful)
            Assert.assertEquals(200, response.code())
        }
    }

}