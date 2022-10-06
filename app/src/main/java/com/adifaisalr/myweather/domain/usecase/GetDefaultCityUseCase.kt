package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDefaultCityUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(): GeoLocationItem =
        withContext(Dispatchers.IO) {
            return@withContext repository.loadDefaultCity()
        }
}