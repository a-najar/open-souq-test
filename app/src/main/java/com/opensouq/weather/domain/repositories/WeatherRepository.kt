package com.opensouq.weather.domain.repositories

import com.opensouq.weather.domain.entities.CurrentWeather
import com.opensouq.weather.domain.entities.Query
import com.opensouq.weather.domain.gateways.WeatherGateWay

interface WeatherRepository {
    suspend fun getWeatherForecast(query: Query): CurrentWeather
}

class WeatherRepositoryImpl(private val weatherGateWay: WeatherGateWay = WeatherGateWay()) :
    WeatherRepository {
    override suspend fun getWeatherForecast(query: Query): CurrentWeather =
        weatherGateWay.getWeathers(query = query, numberOfDays = 1)
}

@Suppress("FunctionName")
fun WeatherRepositories(): WeatherRepository = WeatherRepositoryImpl()