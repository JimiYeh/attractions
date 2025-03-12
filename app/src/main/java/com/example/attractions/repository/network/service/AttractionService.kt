package com.example.attractions.repository.network.service

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.repository.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AttractionService {
    @GET("/{lang}/Attractions/All")
    suspend fun getAllAttractions(@Query("page") page: Int): NetworkResponse<RespAttractionsAll>
}