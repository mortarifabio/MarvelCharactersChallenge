package com.mortarifabio.marvelcharacterschallenge.favorites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.databinding.AdapterCharactersItemBinding
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class FavoritesAdapter (
    private val favorites: MutableList<CharactersResult>,
    private val onItemClicked: (CharactersResult, Boolean?) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCharactersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) { position ->
            favorites.removeAt(position)
            notifyItemChanged(position)
            notifyItemRangeRemoved(position, 1)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }


    class ViewHolder(
        private val binding: AdapterCharactersItemBinding,
        private val removeFavoriteAt: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: CharactersResult?, onItemClicked: (CharactersResult, Boolean?) -> Unit) = with(binding) {
            favorite?.let { character ->
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
                    onItemClicked(character, false)
                    removeFavoriteAt(adapterPosition)
                }
            }
        }
    }
}
