package com.example.paging3tutorial.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.use_case.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersByNameUseCase: GetCharactersByNameUseCase,
    private val query: String,
    private val clearRemoteKeysDao: ClearRemoteKeysDao,
    private val clearReposUseCase: ClearReposUseCase,
    private val insertAllUseCase: InsertAllUseCase,
    private val insertRemoteKeysUseCase: InsertRemoteKeysUseCase,
    private val remoteKeysRepoIdUseCase: RemoteKeysRepoIdUseCase
) : RemoteMediator<Int, ResultEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {


        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = if (query.isEmpty())
                getCharactersUseCase.invoke(page) else getCharactersByNameUseCase.invoke(page,query)
            val repos = apiResponse.resultDtos
            val endOfPaginationReached = repos.isEmpty()

            if (loadType == LoadType.REFRESH) {
                clearRemoteKeysDao.invoke()
                clearReposUseCase.invoke()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = repos.map {
                RemoteKeys(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
            }
            insertRemoteKeysUseCase.invoke(keys)
            insertAllUseCase.invoke(repos.map {
                ResultEntity(
                    it.created,
                    it.gender,
                    it.id,
                    it.image,
                    it.name,
                    it.species,
                    it.status,
                    it.type,
                    it.url
                )
            })

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                remoteKeysRepoIdUseCase.invoke(repo.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ResultEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                remoteKeysRepoIdUseCase.invoke(repo.id.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysRepoIdUseCase.invoke(repoId.toLong())
            }
        }
    }
}