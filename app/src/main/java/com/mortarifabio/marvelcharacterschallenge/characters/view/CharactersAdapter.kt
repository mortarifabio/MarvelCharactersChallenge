package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.databinding.AdapterCharactersItemBinding
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class CharactersAdapter(
    private val onItemClicked: (CharactersResult, Boolean?) -> Unit
) : PagingDataAdapter<CharactersResult, CharactersAdapter.ViewHolder>(CharactersResult) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }

    class ViewHolder(
        private val binding: AdapterCharactersItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: CharactersResult?, onItemClicked: (CharactersResult, Boolean?) -> Unit) = with(binding) {
            result?.let { character ->
                Glide.with(itemView.context)
                    .load(character.smallImage)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .centerCrop()
                    .into(ivAdapterCharactersItemThumbnail)
                tvAdapterCharactersItemName.text = character.name
                mcvAdapterCharactersItem.setOnClickListener {
                    onItemClicked(character, null)
                }
                cbAdapterCharactersItemFavorite.isChecked = character.favorite
                cbAdapterCharactersItemFavorite.setOnClickListener {
                    onItemClicked(character, !character.favorite)
                }
            }
        }
    }
}