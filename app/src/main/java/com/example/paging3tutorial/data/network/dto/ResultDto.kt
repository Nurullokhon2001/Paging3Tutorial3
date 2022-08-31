package com.example.paging3tutorial.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultDto(
    @Json(name = "created")
    val created: String,
    @Json(name = "episode")
    val episode: List<String>,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "location")
    val locationDto: LocationDto,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin")
    val originDto: OriginDto,
    @Json(name = "species")
    val species: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)