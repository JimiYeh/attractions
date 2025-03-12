package com.example.attractions.repository.network

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.IRepository
import com.example.attractions.repository.NetworkResponse

class NetworkRepository(private val client: Client) : IRepository{
    override suspend fun getAllAttractions(): NetworkResponse<RespAttractionsAll> =
        client.attractionService.getAllAttractions()

    override suspend fun getNewsEvents(): NetworkResponse<RespEventsNews> =
        client.eventService.getNewsEvents()
}