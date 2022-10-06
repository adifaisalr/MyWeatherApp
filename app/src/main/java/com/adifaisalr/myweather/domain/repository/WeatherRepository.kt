package com.adifaisalr.myweather.domain.repository

import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.ForecastWeather
import com.adifaisalr.myweather.domain.model.GeoLocationItem

interface WeatherRepository {
    suspend fun searchCity(query: String, limit: Int): DataHolder<List<GeoLocationItem>>
    suspend fun getCurrentWeather(cityCode: String): DataHolder<CurrentWeather>
    suspend fun getForecastWeather(cityCode: String, cnt: Int): DataHolder<ForecastWeather>
    suspend fun insertCity(city: GeoLocationItem): Long
    suspend fun insertCities(cities: List<GeoLocationItem>): List<Long>
    suspend fun loadAllCities(): List<GeoLocationItem>
    suspend fun loadFavoriteCities(): List<GeoLocationItem>
    suspend fun loadDefaultCity(): GeoLocationItem
    suspend fun updateCity(city: GeoLocationItem): Int
    suspend fun deleteAllCities(): Int
}