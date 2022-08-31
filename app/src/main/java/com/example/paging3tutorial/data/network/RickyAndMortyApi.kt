package com.example.paging3tutorial.data.network

import com.example.paging3tutorial.data.network.dto.RickyAndMortyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickyAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page")
        page: Int?
    ): RickyAndMortyDto

    @GET("character/")
    suspend fun getCharactersByName(
        @Query("page")
        page: Int,
        @Query("name")
        characterName: String
    ): RickyAndMortyDto

    companion object {
        const val BASE_URL_API = "https://rickandmortyapi.com/api/"
    }

}