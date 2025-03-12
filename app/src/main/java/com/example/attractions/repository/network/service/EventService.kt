package com.example.attractions.repository.network.service

import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {

    @GET("/{lang}/Events/News")
    suspend fun getNewsEvents(): NetworkResponse<RespEventsNews>
}