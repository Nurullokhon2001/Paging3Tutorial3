package com.example.paging3tutorial.domain.use_case

import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class InsertRemoteKeysUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(remoteKey: List<RemoteKeys>) {
        localRepository.insertRemoteKeys(remoteKey)
    }
}