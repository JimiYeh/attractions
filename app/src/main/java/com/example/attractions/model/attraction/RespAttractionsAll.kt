package com.example.attractions.model.attraction


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RespAttractionsAll(
    @SerializedName("data")
    val attractions: List<Attraction> = listOf(),
    @SerializedName("total")
    val total: Int = 0
) : Serializable {
    data class Attraction(
        @SerializedName("address")
        val address: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("images")
        val images: List<Image> = listOf(),
        @SerializedName("introduction")
        val introduction: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("official_site")
        val officialSite: String = "",
        @SerializedName("open_time")
        val openTime: String = "",
        @SerializedName("tel")
        val tel: String = "",
        @SerializedName("url")
        val url: String = "",
    ) : Serializable {
        data class Image(
            @SerializedName("src")
            val src: String = "",
            @SerializedName("subject")
            val subject: String = "",
            @SerializedName("ext")
            val ext: String = ""
        ) : Serializable
    }
}