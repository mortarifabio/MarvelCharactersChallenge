package com.mortarifabio.marvelcharacterschallenge.favorites

import android.content.Context
import com.mortarifabio.marvelcharacterschallenge.extensions.toCharactersResultMutableList
import com.mortarifabio.marvelcharacterschallenge.extensions.toFavorite
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class FavoritesBusiness(
    private val context: Context
) {

    private val repository by lazy {
        FavoritesRepository(context)
    }

    suspend fun loadFavorites(): MutableList<CharactersResult> {
        val favorites = repository.loadFavorites()
        return favorites.toCharactersResultMutableList()
    }

    suspend fun removeFavorite(character: CharactersResult?) : Int {
        character?.let {
            repository.removeFavorite(it.toFavorite())
        }
        return repository.getFavoritesCount()
    }
}