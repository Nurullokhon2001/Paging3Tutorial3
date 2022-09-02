package com.example.paging3tutorial.domain.use_case

import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class ClearReposUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() {
        localRepository.clearRepos()
    }
}