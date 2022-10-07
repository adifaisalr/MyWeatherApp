package com.adifaisalr.myweather.data.repository

import com.adifaisalr.myweather.data.api.WeatherService
import com.adifaisalr.myweather.data.db.WeatherDao
import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.ForecastWeather
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl(
    private val service: WeatherService,
    private val dao: WeatherDao
) : WeatherRepository {
    override suspend fun searchCity(query: String, limit: Int): DataHolder<List<GeoLocationItem>> {
        return service.searchCity(query, limit)
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): DataHolder<CurrentWeather> {
        return service.getCurrentWeather(lat, lon)
    }

    override suspend fun getForecastWeather(cityCode: String, cnt: Int): DataHolder<ForecastWeather> {
        return service.getForecastWeather(cityCode, cnt)
    }

    override suspend fun insertCity(city: GeoLocationItem): Long {
        return dao.insertCity(city)
    }

    override suspend fun insertCities(cities: List<GeoLocationItem>): List<Long> {
        return dao.insertAllCities(cities)
    }

    override suspend fun loadAllCities(): List<GeoLocationItem> {
        return dao.getAllCitites()
    }

    override suspend fun loadFavoriteCities(): List<GeoLocationItem> {
        return dao.getFavoriteCities()
    }

    override suspend fun loadDefaultCity(): GeoLocationItem {
        return dao.getDefaultCity()
    }

    override suspend fun updateCity(city: GeoLocationItem): Int {
        return dao.updateCity(city)
    }

    override suspend fun deleteAllCities(): Int {
        return dao.deleteAllCities()
    }

    override suspend fun resetDefaultCity(): Int {
        return dao.resetDefaultCity()
    }

    override suspend fun loadCitiesByName(name: String): List<GeoLocationItem> {
        return dao.getCitiesByName(name)
    }
}