package com.adifaisalr.myweather.data.db.converter

import androidx.room.TypeConverter
import com.adifaisalr.myweather.domain.model.GeoLocationItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalNamesDataConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromLocalNames(value: GeoLocationItem.LocalNames?): String {
        val type = object : TypeToken<GeoLocationItem.LocalNames>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLocalNames(value: String): GeoLocationItem.LocalNames? {
        val type = object : TypeToken<GeoLocationItem.LocalNames>() {}.type
        return gson.fromJson(value, type)
    }
}