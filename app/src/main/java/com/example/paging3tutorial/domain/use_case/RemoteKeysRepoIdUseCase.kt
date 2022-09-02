package com.example.paging3tutorial.domain.use_case

import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class RemoteKeysRepoIdUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(repoId: Long): RemoteKeys? {
        return localRepository.remoteKeysRepoId(repoId)
    }
}