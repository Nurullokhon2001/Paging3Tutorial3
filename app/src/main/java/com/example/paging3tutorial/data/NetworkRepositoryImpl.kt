package com.example.paging3tutorial.data

import com.example.paging3tutorial.data.network.RickyAndMortyApi
import com.example.paging3tutorial.data.network.dto.RickyAndMortyDto
import com.example.paging3tutorial.domain.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val rickyAndMortyApi: RickyAndMortyApi
) : NetworkRepository {
    override suspend fun getCharacters(page: Int?): RickyAndMortyDto {
        return rickyAndMortyApi.getCharacters(page)
    }

    override suspend fun getCharactersByName(page: Int, characterName: String): RickyAndMortyDto {
        return rickyAndMortyApi.getCharactersByName(page, characterName)
    }

}