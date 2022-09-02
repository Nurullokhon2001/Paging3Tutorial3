package com.example.paging3tutorial

import androidx.paging.PagingSource
import com.example.paging3tutorial.data.local.dao.RemoteKeysDao
import com.example.paging3tutorial.data.local.dao.ResultEntityDao
import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val remoteKeysDao: RemoteKeysDao,
    private val resultEntityDao: ResultEntityDao
) : LocalRepository {
    override suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>) {
        remoteKeysDao.insertRemoteKeys(remoteKey)
    }

    override suspend fun remoteKeysRepoId(repoId: Long): RemoteKeys? {
        return remoteKeysDao.remoteKeysRepoId(repoId)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun insertAll(repos: List<ResultEntity>) {
        resultEntityDao.insertAll(repos)
    }

    override fun getAllData(): PagingSource<Int, ResultEntity> {
        return resultEntityDao.getAllData()
    }

    override suspend fun clearRepos() {
        resultEntityDao.clearRepos()
    }
}