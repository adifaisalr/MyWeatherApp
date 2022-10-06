package com.adifaisalr.myweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adifaisalr.myweather.data.db.converter.LocalNamesDataConverter
import com.adifaisalr.myweather.domain.model.GeoLocationItem

/**
 * Main database description.
 */
@Database(
    entities = [
        GeoLocationItem::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    LocalNamesDataConverter::class
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
