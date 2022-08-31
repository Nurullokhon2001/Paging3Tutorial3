package com.example.paging3tutorial.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RickyAndMortyDto(
    @Json(name = "info")
    val infoDto: InfoDto,
    @Json(name = "results")
    val resultDtos: List<ResultDto>
)