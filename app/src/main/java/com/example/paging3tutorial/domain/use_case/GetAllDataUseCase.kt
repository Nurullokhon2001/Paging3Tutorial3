package com.example.paging3tutorial.domain.use_case

import androidx.paging.PagingSource
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class GetAllDataUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke(): PagingSource<Int, ResultEntity> {
        return localRepository.getAllData()
    }
}