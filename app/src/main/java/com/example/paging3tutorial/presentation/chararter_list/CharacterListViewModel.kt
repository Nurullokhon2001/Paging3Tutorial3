package com.example.paging3tutorial.presentation.chararter_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3tutorial.data.CharacterRemoteMediator
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val clearRemoteKeysDao: ClearRemoteKeysDao,
    private val clearReposUseCase: ClearReposUseCase,
    private val insertAllUseCase: InsertAllUseCase,
    private val insertRemoteKeysUseCase: InsertRemoteKeysUseCase,
    private val remoteKeysRepoIdUseCase: RemoteKeysRepoIdUseCase,
    private val getAllDataUseCase: GetAllDataUseCase
) : ViewModel() {

//    @OptIn(ExperimentalPagingApi::class)
//    val flow = Pager(
//        config = PagingConfig(
//            pageSize = 10,
//        ),
//        remoteMediator = CharacterRemoteMediator(
//            getCharactersUseCase,
//            repoDatabase
//        ),
//        pagingSourceFactory = { repoDatabase.reposDao().reposByName() }
//    ).flow.cachedIn(viewModelScope)

    @ExperimentalPagingApi
    fun getCatsFromMediator(): Flow<PagingData<ResultEntity>> {
        val pagingSourceFactory = { getAllDataUseCase.invoke() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CharacterRemoteMediator(
                getCharactersUseCase,
                clearRemoteKeysDao,
                clearReposUseCase,
                insertAllUseCase,
                insertRemoteKeysUseCase,
                remoteKeysRepoIdUseCase,
            ),
        ).flow.cachedIn(viewModelScope)
    }
}