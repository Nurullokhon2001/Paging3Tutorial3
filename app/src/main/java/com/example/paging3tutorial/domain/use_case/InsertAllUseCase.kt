package com.example.paging3tutorial.domain.use_case

import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class InsertAllUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(repos: List<ResultEntity>) {
        localRepository.insertAll(repos)
    }
}