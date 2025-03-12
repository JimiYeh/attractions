package com.example.attractions.model.attraction


import com.google.gson.annotations.SerializedName

data class RespAttractionsAll(
    @SerializedName("data")
    val attractions: List<Attraction> = listOf(),
    @SerializedName("total")
    val total: Int = 0
) {
    data class Attraction(
        @SerializedName("address")
        val address: String = "",
        @SerializedName("category")
        val category: List<Category> = listOf(),
        @SerializedName("distric")
        val distric: String = "",
        @SerializedName("elong")
        val elong: Double = 0.0,
        @SerializedName("email")
        val email: String = "",
        @SerializedName("facebook")
        val facebook: String = "",
        @SerializedName("fax")
        val fax: String = "",
        @SerializedName("files")
        val files: List<Any> = listOf(),
        @SerializedName("friendly")
        val friendly: List<Any> = listOf(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("images")
        val images: List<Image> = listOf(),
        @SerializedName("introduction")
        val introduction: String = "",
        @SerializedName("links")
        val links: List<Any> = listOf(),
        @SerializedName("modified")
        val modified: String = "",
        @SerializedName("months")
        val months: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("name_zh")
        val nameZh: Any = Any(),
        @SerializedName("nlat")
        val nlat: Double = 0.0,
        @SerializedName("official_site")
        val officialSite: String = "",
        @SerializedName("open_status")
        val openStatus: Int = 0,
        @SerializedName("open_time")
        val openTime: String = "",
        @SerializedName("remind")
        val remind: String = "",
        @SerializedName("service")
        val service: List<Any> = listOf(),
        @SerializedName("staytime")
        val staytime: String = "",
        @SerializedName("target")
        val target: List<Target> = listOf(),
        @SerializedName("tel")
        val tel: String = "",
        @SerializedName("ticket")
        val ticket: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("zipcode")
        val zipcode: String = ""
    ) {
        data class Category(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = ""
        )

        data class Target(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = ""
        )
        data class Image(
            @SerializedName("src")
            val src: String = "",
            @SerializedName("subject")
            val subject: String = "",
            @SerializedName("ext")
            val ext: String = ""
        )
    }
}