package com.adifaisalr.myweather

import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient
import timber.log.Timber
import timber.log.Timber.DebugTree

class DebugMyWeatherApp : MyWeatherApp() {

    override fun onCreate() {
        SoLoader.init(this, false)
        Timber.plant(DebugTree())
        FlipperWrapper.setup(this)
        super.onCreate()
    }

    override fun httpClientBuilder(): OkHttpClient.Builder {
        return super.httpClientBuilder()
            .addNetworkInterceptor(FlipperWrapper.flipperNetworkInterceptor)
    }
}