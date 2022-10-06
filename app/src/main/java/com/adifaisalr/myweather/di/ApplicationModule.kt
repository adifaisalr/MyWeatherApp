package com.adifaisalr.myweather.di

import android.app.Application
import androidx.room.Room
import com.adifaisalr.myweather.data.api.Api
import com.adifaisalr.myweather.data.api.MyCallAdapterFactory
import com.adifaisalr.myweather.data.api.WeatherService
import com.adifaisalr.myweather.data.db.WeatherDao
import com.adifaisalr.myweather.data.db.WeatherDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(Api.DEFAULT_BASE_URL)
            .client(Api.getDefaultClient())
            .addCallAdapterFactory(MyCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDb {
        return Room
            .databaseBuilder(app, WeatherDb::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }
}
