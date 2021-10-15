package com.opensouq.weather.ui.weather

import androidx.lifecycle.ViewModel
import com.opensouq.weather.domain.entities.CurrentWeather.Data.Weather
import com.opensouq.weather.domain.entities.Query
import com.opensouq.weather.domain.usecases.getWeatherByPlaceName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherViewModel(
    private val getWeather: suspend (Query) -> Weather = {
        getWeatherByPlaceName(it)
    }
) : ViewModel() {

    fun getWeatherFlow(query: Query): Flow<Weather> = flow { emit(getWeather(query)) }
}