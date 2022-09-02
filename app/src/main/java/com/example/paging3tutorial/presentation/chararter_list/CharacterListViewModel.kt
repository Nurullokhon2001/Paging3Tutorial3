package com.example.paging3tutorial.presentation.chararter_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paging3tutorial.data.CharacterPagingSource
import com.example.paging3tutorial.data.CharacterRemoteMediator
import com.example.paging3tutorial.data.local.RickyDb
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.domain.use_case.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val repoDatabase: RickyDb
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
        val pagingSourceFactory = { repoDatabase.reposDao().reposByName() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory =pagingSourceFactory,
            remoteMediator = CharacterRemoteMediator(
                getCharactersUseCase,
                repoDatabase
            ),
        ).flow.cachedIn(viewModelScope)
    }
}