package com.example.attractions.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    var language: String = "zh-tw"  // 默認語言

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        
        // 使用已編碼的佔位符進行替換
        val newUrl = originalUrl.newBuilder()
            .encodedPath(originalUrl.encodedPath()
                .replace("%7Blang%7D", language)  // 替換編碼後的佔位符
                .replace("{lang}", language))     // 以防萬一也替換未編碼的佔位符
            .build()
        
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
            
        return chain.proceed(newRequest)
    }
} 