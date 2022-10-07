package com.adifaisalr.myweather.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adifaisalr.myweather.domain.model.CurrentWeather
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.usecase.GetCurrentWeatherUseCase
import com.adifaisalr.myweather.domain.usecase.GetForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _currentWeatherResult: MutableLiveData<DataHolder<CurrentWeather>> =
        MutableLiveData<DataHolder<CurrentWeather>>()
    val currentWeatherResult: LiveData<DataHolder<CurrentWeather>>
        get() = _currentWeatherResult

    var lat: Double = 0.0
    var lon: Double = 0.0

    fun getCurrentWeather() = viewModelScope.launch {
        _currentWeatherResult.postValue(DataHolder.Loading)
        val response = getCurrentWeatherUseCase(lat, lon)
        _currentWeatherResult.postValue(response)
    }
}