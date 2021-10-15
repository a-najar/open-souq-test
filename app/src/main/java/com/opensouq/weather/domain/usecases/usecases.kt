package com.opensouq.weather.domain.usecases

import com.opensouq.weather.domain.entities.CurrentWeather.Data.Weather
import com.opensouq.weather.domain.entities.Query
import com.opensouq.weather.domain.repositories.WeatherRepositories
import com.opensouq.weather.domain.repositories.WeatherRepository


private val weathersCache = mutableMapOf<String, Weather>()

suspend fun getWeatherByPlaceName(
    query: Query,
    weatherRepository: WeatherRepository = WeatherRepositories(),
    cache: () -> MutableMap<String, Weather> = { weathersCache }
): Weather = cache()[query] ?: weatherRepository
    .getWeatherForecast(query)
    .data.weather
    .firstOrNull()
    ?.also { cache()[query] = it } ?: throw CantFindAnyWeather

object CantFindAnyWeather : Throwable()