package com.example.paging3tutorial.presentation.chararter_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.paging3tutorial.R
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.data.network.dto.ResultDto
import com.example.paging3tutorial.databinding.FragmentCharacterListBinding
import com.example.paging3tutorial.utills.Extensions.sadeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val characterListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterListAdapter(
            itemClick = {itemClicked(it)}
        )
    }

    private val characterListViewModel by viewModels<CharacterListViewModel>()

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterListBinding.inflate(layoutInflater)


        binding.apply {
            rvCharacters.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = characterListAdapter.withLoadStateHeaderAndFooter(
                    header = CharacterLoaderStateAdapter(),
                    footer = CharacterLoaderStateAdapter()
                )
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterListViewModel.getCatsFromMediator()
                    .collectLatest {
                        characterListAdapter.submitData(it)
                    }
            }
        }
        return binding.root
    }

    private fun itemClicked(character: ResultEntity) {
        findNavController().sadeNavigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                character
            )
        )
    }
}