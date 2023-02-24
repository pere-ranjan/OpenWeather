package com.ranjan.openweather.data.database.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "Weather")
data class Weather(
    var visibility: Long? = null,
    var timezone: Long? = null,
    @Embedded var main: MainModel? = null,
    @Embedded var clouds: CloudsModel? = null,
    @Embedded var sysModel: SysModel? = null,
    @Embedded var coordinate: Coordinate,
    @Embedded var weatherItemList: WeatherItemModel? = null,
    var name: String? = null,
    var cod: Int? = null,
    var base: String? = null,
    var dt: Int? = null,
    @Embedded var wind: WindModel? = null,
    var time: String
) {
    @PrimaryKey(autoGenerate = true)
    var dataId: Long? = null
}

data class MainModel(
    var temp: String? = null,
    var minTemp: String? = null,
    var groundLevel: Int? = null,
    var humidity: String? = null,
    var pressure: String? = null,
    var seaLevel: Int? = null,
    var feelsLike: String? = null,
    var maxTemp: String? = null,
)

data class SysModel(
    var sunrise: String? = null, var sunset: String? = null
)

data class Coordinate(
    var longitude: Double, var latitude: Double
)

data class WindModel(
    var windDeg: Long? = null, var speed: Double? = null, var gust: Double? = null
)

data class CloudsModel(
    var all: Long? = null
)

data class WeatherItemModel(
    var icon: String? = null,
    var description: String? = null,
    var main: String? = null,
    @ColumnInfo("WeatherItemId") var id: Long? = null
)

