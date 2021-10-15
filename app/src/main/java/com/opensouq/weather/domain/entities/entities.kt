package com.opensouq.weather.domain.entities

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


typealias Query = String

@Keep
data class CurrentWeather(
    @SerializedName("data") val data: Data = Data()
) {
    @Keep
    data class Data(
        @SerializedName("weather") val weather: List<Weather> = listOf()
    ) {
        @Keep
        data class Weather(
            @SerializedName("avgtempC") val avgtempC: String = "",
            @SerializedName("avgtempF") val avgtempF: String = "",
            @SerializedName("date") val date: String = "",
            @SerializedName("hourly") val hourly: List<Hourly> = listOf(),
            @SerializedName("sunHour") val sunHour: String = "",
        ) {

            @Keep
            data class Hourly(
                @SerializedName("tempC") val tempC: String = "",
                @SerializedName("tempF") val tempF: String = "",
                @SerializedName("time") val time: String = "",
                @SerializedName("weatherIconUrl") val weatherIconUrl: List<WeatherIconUrl> = listOf(),
            ) {
                @Keep
                data class WeatherIconUrl(
                    @SerializedName("value") val value: String = ""
                )
            }
        }
    }
}