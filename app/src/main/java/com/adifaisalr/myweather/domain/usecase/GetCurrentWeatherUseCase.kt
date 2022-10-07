package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val mediaRepository: WeatherRepository) {
    suspend operator fun invoke(lat: Double, lon: Double): DataHolder<CurrentWeather> =
        withContext(Dispatchers.IO) {
            return@withContext mediaRepository.getCurrentWeather(lat, lon)
        }
}