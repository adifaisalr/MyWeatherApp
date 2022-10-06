package com.adifaisalr.myweather.domain.usecase

import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.ForecastWeather
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(private val mediaRepository: WeatherRepository) {
    suspend operator fun invoke(cityCode: String, cnt: Int): DataHolder<ForecastWeather> =
        withContext(Dispatchers.IO) {
            return@withContext mediaRepository.getForecastWeather(cityCode, cnt)
        }
}