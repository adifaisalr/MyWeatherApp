package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val mediaRepository: WeatherRepository) {
    suspend operator fun invoke(query: String, limit: Int): DataHolder<List<GeoLocationItem>> =
        withContext(Dispatchers.IO) {
            return@withContext mediaRepository.searchCity(query, limit)
        }
}