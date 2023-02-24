package com.ranjan.openweather.data.remote.dto

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.ranjan.openweather.data.database.entities.*
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


data class ResponseDTO(

    @field:SerializedName("visibility") val visibility: Int? = null,

    @field:SerializedName("timezone") val timezone: Int? = null,

    @field:SerializedName("main") val main: Main? = null,

    @field:SerializedName("clouds") val clouds: Clouds? = null,

    @field:SerializedName("sys") val sys: Sys? = null,

    @field:SerializedName("dt") val dt: Int? = null,

    @field:SerializedName("coord") val coord: Coord? = null,

    @field:SerializedName("weather") val weather: List<WeatherItem?>? = null,

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("cod") val cod: Int? = null,

    @field:SerializedName("id") val id: Int? = null,

    @field:SerializedName("base") val base: String? = null,

    @field:SerializedName("wind") val wind: Wind? = null
) {
    @PrimaryKey(autoGenerate = true)
    var dataId: Long? = null
}

data class Main(

    @field:SerializedName("temp") val _temp: Double? = null,

    @field:SerializedName("temp_min") val _tempMin: Double? = null,

    @field:SerializedName("grnd_level") val grndLevel: Int? = null,

    @field:SerializedName("humidity") val _humidity: Int? = null,

    @field:SerializedName("pressure") val _pressure: Int? = null,

    @field:SerializedName("sea_level") val seaLevel: Int? = null,

    @field:SerializedName("feels_like") val _feelsLike: Double? = null,

    @field:SerializedName("temp_max") val _tempMax: Double? = null
)

data class Sys(

    @field:SerializedName("sunrise") val sunrise: Long? = null,

    @field:SerializedName("sunset") val sunset: Long? = null
)

data class Coord(

    @field:SerializedName("lon") val lon: Double? = null,

    @field:SerializedName("lat") val lat: Double? = null
)

data class Wind(

    @field:SerializedName("deg") val deg: Long? = null,

    @field:SerializedName("speed") val speed: Double? = null,

    @field:SerializedName("gust") val gust: Double? = null
)

data class Clouds(

    @field:SerializedName("all") val all: Long? = null
)

data class WeatherItem(

    @field:SerializedName("icon") val icon: String? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("main") val main: String? = null,

    @field:SerializedName("id") @ColumnInfo("WeatherItemId") val id: Int? = null
)


fun ResponseDTO.toWeather(): Weather {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US)
    val date = Date()
    val time: String = dateFormat.format(date)
    return Weather(
        visibility?.toLong(),
        timezone?.toLong(),
        main?.toMainModel(),
        clouds?.toCloudModel(),
        sys?.toSysModel(),
        coord?.toCoordinate()!!,
        weather?.toListOfWeatherModel(),
        name,
        cod,
        base,
        dt,
        wind?.toWindModel(),
        time
    )
}

fun List<WeatherItem?>.toListOfWeatherModel(): WeatherItemModel {
    val iconUrl = "https://openweathermap.org/img/wn/${this[0]?.icon}@4x.png"
    return WeatherItemModel(iconUrl, this[0]?.description, this[0]?.main, this[0]?.id?.toLong())
}

fun Main.toMainModel(): MainModel {
    val maxTemp = "$_tempMax\u2103"
    val minTemp = "$_tempMin\u2103"
    val temp = "${_temp?.toBigDecimal()?.setScale(1, RoundingMode.UP)}\u2103"
    val pressure = "${_pressure}hPA"
    val feelsLike = "Feels like: ${_feelsLike}\u2103"
    val humidity = "${_humidity}%"
    return MainModel(
        temp,
        minTemp,
        grndLevel,
        humidity,
        pressure,
        seaLevel,
        feelsLike,
        maxTemp,
    )
}

fun Clouds.toCloudModel(): CloudsModel {
    return CloudsModel(all)
}

fun Sys.toSysModel() = SysModel(getTime(sunrise), getTime(sunset))


fun Wind.toWindModel() = WindModel(deg, speed, gust)
fun Coord.toCoordinate() = Coordinate(lon!!, lat!!)

private fun getTime(dt: Long?): String? {
    if (dt == null || dt == 0L) return null
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
    val date = Date(dt * 1000)
    return dateFormat.format(date)
}