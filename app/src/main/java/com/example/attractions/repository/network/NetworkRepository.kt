package com.example.attractions.repository.network

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.model.event.RespEventsNews
import com.example.attractions.repository.IRepository
import com.example.attractions.repository.NetworkResponse

class NetworkRepository(private val client: Client) : IRepository {
    override suspend fun getAllAttractions(page: Int): NetworkResponse<RespAttractionsAll> =
        client.attractionService.getAllAttractions(page)

    override suspend fun getNewsEvents(
        begin: String,
        end: String,
        page: Int
    ): NetworkResponse<RespEventsNews> =
        client.eventService.getNewsEvents(begin, end, page)
}