package com.example.paging3tutorial.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String?,
    @Json(name = "pages")
    val pages: Int?,
    @Json(name = "prev")
    val prev: Any?
)