package com.adifaisalr.myweather.data.api

import okhttp3.OkHttpClient

interface ApiBaseConfigurator {
    fun newHttpClientBuilder(): OkHttpClient.Builder
}