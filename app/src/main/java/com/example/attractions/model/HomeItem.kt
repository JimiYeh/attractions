package com.example.attractions.model

import com.example.attractions.model.attraction.RespAttractionsAll
import com.example.attractions.model.event.RespEventsNews


sealed class HomeItem {
    abstract val id: String

    data class AttractionCount(
        val count: Int
    ) : HomeItem() {
        override val id: String = "attraction_count"
    }

    data class Header(
        val title: String,
        override val id: String
    ) : HomeItem()

    data class NewsEventItem(
        val event: RespEventsNews.Event
    ) : HomeItem() {
        override val id: String = "event_${event.id}"
    }

    data class AttractionItem(
        val attraction: RespAttractionsAll.Attraction
    ) : HomeItem() {
        override val id: String = "attraction_${attraction.id}"
    }
} 