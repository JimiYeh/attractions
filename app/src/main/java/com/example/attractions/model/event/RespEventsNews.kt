package com.example.attractions.model.event


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RespEventsNews(
    @SerializedName("data")
    val events: List<Event> = listOf(),
    @SerializedName("total")
    val total: Int = 0
) : Serializable {
    data class Event(
        @SerializedName("description")
        val description: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("url")
        val url: String = ""
    ) : Serializable
}