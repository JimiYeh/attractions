package com.example.attractions.repository

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.model.event.RespEventsNews

interface IRepository {

    // 取得所有景點資料
    suspend fun getAllAttractions(page: Int): NetworkResponse<RespAttractionsAll>

    // 取得所有活動資料
    suspend fun getNewsEvents(begin: String, end: String, page: Int): NetworkResponse<RespEventsNews>
}