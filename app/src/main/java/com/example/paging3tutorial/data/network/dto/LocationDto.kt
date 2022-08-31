package com.example.paging3tutorial.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)