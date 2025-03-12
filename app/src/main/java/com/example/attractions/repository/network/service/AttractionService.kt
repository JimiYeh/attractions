package com.example.attractions.repository.network.service

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.repository.NetworkResponse
import retrofit2.http.GET

interface AttractionService {
    @GET("/{lang}/Attractions/All")
    suspend fun getAllAttractions(): NetworkResponse<RespAttractionsAll>
}