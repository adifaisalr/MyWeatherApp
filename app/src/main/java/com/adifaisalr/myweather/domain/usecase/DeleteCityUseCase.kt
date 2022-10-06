package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(): Int = withContext(Dispatchers.IO) {
        return@withContext repository.deleteAllCities()
    }
}