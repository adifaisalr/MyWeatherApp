package com.adifaisalr.myweather.presentation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.usecase.GetFavoriteCityUseCase
import com.adifaisalr.myweather.domain.usecase.ResetDefaultCityUseCase
import com.adifaisalr.myweather.domain.usecase.UpdateCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val updateCityUseCase: UpdateCityUseCase,
    val getFavoriteCityUseCase: GetFavoriteCityUseCase,
    private val resetDefaultCityUseCase: ResetDefaultCityUseCase
) : ViewModel() {

    private val _cities: MutableLiveData<DataHolder<List<GeoLocationItem>>> =
        MutableLiveData<DataHolder<List<GeoLocationItem>>>()
    val cities: LiveData<DataHolder<List<GeoLocationItem>>>
        get() = _cities

    fun updateCity(city: GeoLocationItem) = viewModelScope.async {
        updateCityUseCase(city)
    }

    fun loadFavoriteCities() = viewModelScope.launch {
        _cities.postValue(DataHolder.Loading)
        _cities.postValue(DataHolder.Success(getFavoriteCityUseCase()))
    }

    fun resetDefaultCity() = viewModelScope.async {
        resetDefaultCityUseCase()
    }
}