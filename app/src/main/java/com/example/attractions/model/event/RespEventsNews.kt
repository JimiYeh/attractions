package com.example.attractions.model.event


import com.google.gson.annotations.SerializedName

data class RespEventsNews(
    @SerializedName("data")
    val events: List<Event> = listOf(),
    @SerializedName("total")
    val total: Int = 0
) {
    data class Event(
        @SerializedName("begin")
        val begin: Any = Any(),
        @SerializedName("description")
        val description: String = "",
        @SerializedName("end")
        val end: Any = Any(),
        @SerializedName("files")
        val files: List<Any> = listOf(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("links")
        val links: List<Link> = listOf(),
        @SerializedName("modified")
        val modified: String = "",
        @SerializedName("posted")
        val posted: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("url")
        val url: String = ""
    ) {
        data class Link(
            @SerializedName("src")
            val src: String = "",
            @SerializedName("subject")
            val subject: String = ""
        )
    }
}