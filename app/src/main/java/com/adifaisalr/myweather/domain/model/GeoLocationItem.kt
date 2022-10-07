package com.adifaisalr.myweather.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.TypeConverters
import com.adifaisalr.myweather.data.db.converter.LocalNamesDataConverter
import com.google.gson.annotations.SerializedName

@Keep
@Entity(primaryKeys = ["name", "lat", "lon"])
data class GeoLocationItem(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("local_names")
    @TypeConverters(LocalNamesDataConverter::class)
    val localNames: LocalNames? = LocalNames(""),
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String?="",
    var isFavorite: Boolean = false,
    var isDefault: Boolean = false
) {
    @Keep
    data class LocalNames(
        @SerializedName("en")
        val en: String
    )
}