package com.example.paging3tutorial.domain

import androidx.paging.PagingSource
import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.data.local.entity.ResultEntity

interface LocalRepository {

    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>)

    suspend fun remoteKeysRepoId(repoId: Long): RemoteKeys?

    suspend fun clearRemoteKeys()

    suspend fun insertAll(repos: List<ResultEntity>)

    fun getAllData(): PagingSource<Int, ResultEntity>

    suspend fun clearRepos()
}