package com.mortarifabio.marvelcharacterschallenge.characters.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.databinding.AdapterCharactersItemBinding
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult.Companion.DIFF_CALLBACK

class CharactersAdapter(
    private val onItemClicked: (CharactersResult?) -> Unit
) : PagedListAdapter<CharactersResult, CharactersAdapter.ViewHolder>(DIFF_CALLBACK) {
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
        fun bind(result: CharactersResult?, onItemClicked: (CharactersResult?) -> Unit) {
            result?.let { character ->
                Glide.with(itemView.context)
                    .load(character.smallImage)
                    .placeholder(R.drawable.thumbnail_placeholder)
                    .centerCrop()
                    .into(binding.ivAdapterCharactersItemThumbnail)
                binding.tvAdapterCharactersItemName.text = character.name
                binding.mcvAdapterCharactersItem.setOnClickListener {
                    onItemClicked(character)
                }
            }
        }
    }
}