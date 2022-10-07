package com.adifaisalr.myweather.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.adifaisalr.myweather.domain.model.GeoLocationItem

/**
 * Interface for database access for Bank related operations.
 */
@Dao
abstract class WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCity(city: GeoLocationItem): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAllCities(cities: List<GeoLocationItem>): List<Long>

    @Query("DELETE FROM `GeoLocationItem`")
    abstract suspend fun deleteAllCities(): Int

    @Query("SELECT * FROM `GeoLocationItem`")
    abstract fun getAllCitites(): List<GeoLocationItem>

    @Query("SELECT * FROM `GeoLocationItem` WHERE name LIKE '%' || :name || '%'")
    abstract fun getCitiesByName(name: String): List<GeoLocationItem>

    @Query("SELECT * FROM `GeoLocationItem` WHERE isFavorite = 1")
    abstract fun getFavoriteCities(): List<GeoLocationItem>

    @Query("SELECT * FROM `GeoLocationItem` WHERE isDefault = 1")
    abstract fun getDefaultCity(): GeoLocationItem

    @Query("UPDATE `GeoLocationItem` SET isDefault = 0")
    abstract fun resetDefaultCity(): Int

    @Update
    abstract suspend fun updateCity(city: GeoLocationItem): Int
}
