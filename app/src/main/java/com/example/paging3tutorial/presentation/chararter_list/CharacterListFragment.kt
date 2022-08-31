package com.example.paging3tutorial.presentation.chararter_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.paging3tutorial.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val characterListViewModel by viewModels<CharacterListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterListViewModel.getCharactersUseCase()
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }
}