package com.example.attractions.repository.network.service

import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    @GET("/{lang}/Events/News")
    suspend fun getNewsEvents(
        @Query("begin") begin: String,
        @Query("end") end: String,
        @Query("page") page: Int
    ): NetworkResponse<RespEventsNews>
}