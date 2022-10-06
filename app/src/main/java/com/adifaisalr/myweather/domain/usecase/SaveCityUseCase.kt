package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCityUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(city: GeoLocationItem): Long = withContext(Dispatchers.IO) {
        return@withContext repository.insertCity(city)
    }

    suspend operator fun invoke(cities: List<GeoLocationItem>): List<Long> = withContext(Dispatchers.IO) {
        return@withContext repository.insertCities(cities)
    }
}