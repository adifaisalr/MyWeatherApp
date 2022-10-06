package com.adifaisalr.myweather.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adifaisalr.myweather.domain.model.DataHolder
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.adifaisalr.myweather.domain.usecase.GetAllCityUseCase
import com.adifaisalr.myweather.domain.usecase.SaveCityUseCase
import com.adifaisalr.myweather.domain.usecase.SearchCityUseCase
import com.adifaisalr.myweather.domain.usecase.UpdateCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchUseCase: SearchCityUseCase,
    val saveCityUseCase: SaveCityUseCase,
    val updateCityUseCase: UpdateCityUseCase,
    val loadAllCityUseCase: GetAllCityUseCase
) : ViewModel() {

    private var _query = ""
    val query: String
        get() = _query

    private val _searchResult: MutableLiveData<DataHolder<List<GeoLocationItem>>> =
        MutableLiveData<DataHolder<List<GeoLocationItem>>>()
    val searchResult: LiveData<DataHolder<List<GeoLocationItem>>>
        get() = _searchResult

    fun setQuery(originalInput: String) {
        val input = originalInput.lowercase(Locale.getDefault()).trim()
        if (input == _query) {
            return
        }
        _query = input
    }

    fun searchMedia() = viewModelScope.launch {
        _searchResult.postValue(DataHolder.Loading)
        val response = searchUseCase(query, SEARCH_LIMIT)
        response.peekData?.let {
            insertCities(it).await()
            val cities = loadAllCityUseCase()
            _searchResult.postValue(DataHolder.Success(cities))
        } ?: _searchResult.postValue(response)
    }

    private fun insertCities(cities: List<GeoLocationItem>) = viewModelScope.async {
        saveCityUseCase(cities)
    }

    fun updateCity(city: GeoLocationItem) = viewModelScope.async {
        updateCityUseCase(city)
    }

    companion object {
        const val SEARCH_LIMIT = 5
    }
}