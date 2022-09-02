package com.example.paging3tutorial.presentation.chararter_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paging3tutorial.data.network.dto.ResultDto
import com.example.paging3tutorial.databinding.CharacterListItemBinding

class CharacterListAdapter : PagingDataAdapter<ResultDto,CharacterListAdapter.CharacterViewHolder>(CharacterComparator()) {

  inner  class CharacterViewHolder(private val binding : CharacterListItemBinding):
      RecyclerView.ViewHolder(binding.root) {
      fun bindCharacter(character: ResultDto) {
          binding.apply {
              tvCharacterName.text = character.name
              tvCharacterId.text = character.id.toString()
              imgvCharacterImage.load(character.image)
          }
      }
  }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindCharacter(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    class CharacterComparator : DiffUtil.ItemCallback<ResultDto>() {
        override fun areItemsTheSame(oldItem: ResultDto, newItem: ResultDto) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ResultDto, newItem: ResultDto) =
            oldItem.id == newItem.id && oldItem.name == oldItem.name
    }
}