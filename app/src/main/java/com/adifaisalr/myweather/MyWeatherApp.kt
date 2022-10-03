package com.adifaisalr.myweather

import android.app.Application
import com.adifaisalr.myweather.data.api.Api
import com.adifaisalr.myweather.data.api.ApiBaseConfigurator
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
open class MyWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Api.setBaseConfigurator(object : ApiBaseConfigurator {
            override fun newHttpClientBuilder(): OkHttpClient.Builder = httpClientBuilder()
        })
    }

    open fun httpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}