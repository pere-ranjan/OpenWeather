package com.ranjan.openweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ranjan.openweather.data.database.entities.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun saveData(weather: Weather?)

    @Query("Select * from Weather")
    fun getSavedWeatherInformation(): Flow<List<Weather>>
}