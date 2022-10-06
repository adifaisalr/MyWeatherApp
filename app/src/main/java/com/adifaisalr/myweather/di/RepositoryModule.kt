package com.adifaisalr.tmdbapplication.di

import com.adifaisalr.myweather.data.api.WeatherService
import com.adifaisalr.myweather.data.db.WeatherDao
import com.adifaisalr.myweather.data.repository.WeatherRepositoryImpl
import com.adifaisalr.myweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(service: WeatherService, dao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(service, dao)
    }
}