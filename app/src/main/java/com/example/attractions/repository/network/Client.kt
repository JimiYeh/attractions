package com.example.attractions.repository.network

import com.example.attractions.BuildConfig
import com.example.attractions.repository.network.interceptor.LanguageInterceptor
import com.example.attractions.repository.network.service.AttractionService
import com.example.attractions.repository.network.service.EventService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client(languageInterceptor: LanguageInterceptor) {

    val attractionService: AttractionService
    val eventService: EventService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val requestBuilder: Request.Builder =
                    it.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .url(it.request().url())

                it.proceed((requestBuilder.build()))
            }
            .addInterceptor(languageInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()

        attractionService = retrofit.create(AttractionService::class.java)
        eventService = retrofit.create(EventService::class.java)
    }
}