package com.opensouq.weather.domain.gateways

import com.opensouq.weather.BuildConfig
import com.opensouq.weather.domain.entities.CurrentWeather
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "key"
private const val FORMAT = "format"
private const val JSON_FORMAT = "json"

interface WeatherGateWay {

    @GET("weather.ashx")
    suspend fun getWeathers(
        @Query("num_of_days") numberOfDays: Int = 1,
        @Query("q") query: String = "jordan",
    ): CurrentWeather

    @GET("search.ashx")
    suspend fun search(
        @Query("q") query: String = "jordan",
    ): CurrentWeather
}


@Suppress("FunctionName")
fun WeatherGateWay(): WeatherGateWay = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.API_URL)
    .client(getHttpClient())
    .build()
    .create(WeatherGateWay::class.java)

private fun getHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(getHeaderInterceptor())
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

private fun getHeaderInterceptor() = Interceptor { chain ->
    val request = chain.request()
    val requestBuilder = request.newBuilder()
    val url = request
        .url
        .newBuilder()
        .addQueryParameter(API_KEY, BuildConfig.API_KEY)
        .addQueryParameter(FORMAT, JSON_FORMAT)
        .build()
    requestBuilder.url(url)
    chain.proceed(requestBuilder.build())
}
