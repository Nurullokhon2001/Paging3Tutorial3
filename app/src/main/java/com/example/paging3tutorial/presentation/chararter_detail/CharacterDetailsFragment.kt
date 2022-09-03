package com.example.paging3tutorial.presentation.chararter_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var character: ResultEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)
        getArgs()
        return binding.root
    }

    private fun getArgs() {
        character = args.character
        bindCharacterDetails()
    }

    private fun bindCharacterDetails() {
        setActionBarTitle()
        binding.apply {
            character.let {
                tvCharacterName.text = it.name
                tvCharacterSpecies.text = it.species
                tvCharacterGender.text = it.gender
                tvCharacterStatus.text = it.status
                imgCharacterImage.load(character.image)
            }
        }
    }

    private fun setActionBarTitle() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = character.name
    }
}