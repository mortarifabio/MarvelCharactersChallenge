package com.mortarifabio.marvelcharacterschallenge.details

import android.content.Context
import com.mortarifabio.marvelcharacterschallenge.extensions.toFavorite
import com.mortarifabio.marvelcharacterschallenge.favorites.FavoritesRepository
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class DetailsBusiness(
    private val context: Context
) {

    private val repository by lazy {
        FavoritesRepository(context)
    }

    suspend fun addFavorite(character: CharactersResult?) {
        character?.let {
            repository.addFavorite(it.toFavorite())
        }
    }

    suspend fun removeFavorite(character: CharactersResult?) {
        character?.let {
            repository.removeFavorite(it.toFavorite())
        }
    }
}