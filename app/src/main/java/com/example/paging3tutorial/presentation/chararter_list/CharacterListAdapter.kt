package com.example.paging3tutorial.presentation.chararter_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paging3tutorial.data.local.entity.ResultEntity
import com.example.paging3tutorial.databinding.CharacterListItemBinding

class CharacterListAdapter(
    val itemClick: (ResultEntity) -> Unit
) : PagingDataAdapter<ResultEntity, CharacterListAdapter.CharacterViewHolder>(CharacterComparator()) {

    inner class CharacterViewHolder(private val binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCharacter(character: ResultEntity) {
            binding.apply {
                tvCharacterName.text = character.name
                tvCharacterId.text = character.id.toString()
                imgvCharacterImage.load(character.image)
                imgvCharacterImage.setOnClickListener {
                    itemClick.invoke(character)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindCharacter(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    class CharacterComparator : DiffUtil.ItemCallback<ResultEntity>() {
        override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity) =
            oldItem.id == newItem.id && oldItem.name == oldItem.name
    }
}