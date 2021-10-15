package com.opensouq.weather.domain.usecases


import com.opensouq.weather.domain.entities.CurrentWeather
import com.opensouq.weather.domain.entities.CurrentWeather.Data
import com.opensouq.weather.domain.entities.CurrentWeather.Data.Weather
import com.opensouq.weather.domain.repositories.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetWeatherByPlaceNameTest {
    @Test
    fun `Given City Name when name is correct for city then return list of the weather of the day`() {
        val resultWeather = Weather()
        val currentWeather = CurrentWeather(
            data = Data(
                weather = listOf(resultWeather)
            )
        )

        val repositories = mock<WeatherRepository> {
            onBlocking { getWeatherForecast("amman") } doReturn currentWeather
        }
        val weather =
            runBlocking { getWeatherByPlaceName("amman", repositories) { mutableMapOf() } }
        assertEquals(
            weather,
            resultWeather
        )
    }

    @Test(expected = CantFindAnyWeather::class)
    fun `Given City Name when name is not correct then throw`() {
        val currentWeather = CurrentWeather()
        val repositories = mock<WeatherRepository> {
            onBlocking { getWeatherForecast("amman") } doReturn currentWeather
        }
        runBlocking { getWeatherByPlaceName("amman", repositories) { mutableMapOf() } }
    }
}