package com.adifaisalr.myweather.data.api

import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.ForecastWeather
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface WeatherService {
    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): DataHolder<List<GeoLocationItem>>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): DataHolder<CurrentWeather>

    @GET("data/2.5/forecast")
    suspend fun getForecastWeather(@Query("q") cityCode: String, @Query("cnt") days: Int): DataHolder<ForecastWeather>
}
