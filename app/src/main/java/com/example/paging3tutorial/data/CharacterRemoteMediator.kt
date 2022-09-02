package com.example.paging3tutorial.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.paging3tutorial.data.local.RickyDb
import com.example.paging3tutorial.data.local.entity.RemoteKeys
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.use_case.GetCharactersUseCase
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val repoDatabase: RickyDb
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
            val apiResponse = getCharactersUseCase.invoke(page)
            val repos = apiResponse.resultDtos
            val endOfPaginationReached = repos.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    repoDatabase.remoteKeysDao().clearRemoteKeys()
                    repoDatabase.reposDao().clearRepos()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                repoDatabase.remoteKeysDao().insertAll(keys)
                repoDatabase.reposDao().insertAll(repos.map {
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
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repo.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ResultEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repo.id.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repoId.toLong())
            }
        }
    }
}