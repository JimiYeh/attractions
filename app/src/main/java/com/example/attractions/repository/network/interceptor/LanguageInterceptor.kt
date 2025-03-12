package com.example.attractions.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor : Interceptor {
    var language: String = "zh-tw"  // 默認語言

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val originalUrl = originalRequest.url()
        
        // 構建新的 URL，替換 {lang} 佔位符
        val newUrl = originalUrl.newBuilder()
            .encodedPath(originalUrl.encodedPath().replace("{lang}", language))
            .build()
        
        // 創建新的請求
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
            
        return chain.proceed(newRequest)
    }
} 