package com.example.paging3tutorial.presentation.chararter_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.paging3tutorial.data.network.CharacterPagingSource
import com.example.paging3tutorial.domain.use_case.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    fun getCharactersUseCase() {
        viewModelScope.launch {
            getCharactersUseCase.invoke(1)
        }
    }

    val flow = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false)
    ) {
        CharacterPagingSource(getCharactersUseCase)
    }.flow
        .cachedIn(viewModelScope)

}