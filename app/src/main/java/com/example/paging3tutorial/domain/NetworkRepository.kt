package com.example.paging3tutorial.domain

import com.example.paging3tutorial.data.network.dto.RickyAndMortyDto

interface NetworkRepository {
    suspend fun getCharacters(
        page: Int
    ): RickyAndMortyDto

    suspend fun getCharactersByName(
        page: Int,
        characterName: String
    ): RickyAndMortyDto
}