package com.example.paging3tutorial.presentation.chararter_list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.paging3tutorial.R
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.databinding.FragmentCharacterListBinding
import com.example.paging3tutorial.utills.Extensions.sadeNavigate
import com.example.paging3tutorial.utills.setOnQueryListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val characterListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterListAdapter(
            itemClick = { itemClicked(it) }
        )
    }


    private val characterListViewModel by viewModels<CharacterListViewModel>()

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(layoutInflater)
        getCharacters("")
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val item = menu.findItem(R.id.searchCharacterMenu)
                val searchView = item?.actionView as SearchView
                searchView.queryHint = "Type a character name"
                searchView.setOnQueryListener() {
                    getCharacters(it)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun itemClicked(character: ResultEntity) {
        findNavController().sadeNavigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                character
            )
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun getCharacters(query: String) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterListViewModel.getCatsFromMediator(query)
                    .collectLatest {
                        characterListAdapter.submitData(it)
                    }
            }
        }
    }
}