package com.ranjan.openweather

import java.util.concurrent.ThreadLocalRandom

object DummyLocationFetcher {

    private const val MIN_LATITUDE = -90.0
    private const val MAX_LATITUDE = 90.0
    private const val MIN_LONGITUDE = -180.0
    private const val MAX_LONGITUDE = 180.0


    fun getLatitude(): Double {
        return MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * ThreadLocalRandom.current()
            .nextDouble()
    }

    fun getLongitude(): Double {
        return MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * ThreadLocalRandom.current()
            .nextDouble()
    }
}