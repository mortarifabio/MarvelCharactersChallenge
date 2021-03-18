package com.mortarifabio.marvelcharacterschallenge.favorites

import android.content.Context
import com.mortarifabio.marvelcharacterschallenge.database.MarvelDatabase
import com.mortarifabio.marvelcharacterschallenge.model.Favorite

class FavoritesRepository(
    private val context: Context
) {

    private val favoritesDao by lazy {
        MarvelDatabase.getDatabase(context).favoritesDao()
    }

    suspend fun addFavorite(favorite: Favorite) {
        favoritesDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        favoritesDao.deleteFavorite(favorite)
    }

    suspend fun loadFavorites(): List<Favorite> {
        return favoritesDao.loadFavorites()
    }

}