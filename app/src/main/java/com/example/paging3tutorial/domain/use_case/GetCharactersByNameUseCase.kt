package com.example.paging3tutorial.domain.use_case

import com.example.paging3tutorial.data.network.dto.RickyAndMortyDto
import com.example.paging3tutorial.domain.NetworkRepository
import javax.inject.Inject

class GetCharactersByNameUseCase  @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(page: Int=1,name:String = ""): RickyAndMortyDto {
        return networkRepository.getCharactersByName(page,name)
    }
}