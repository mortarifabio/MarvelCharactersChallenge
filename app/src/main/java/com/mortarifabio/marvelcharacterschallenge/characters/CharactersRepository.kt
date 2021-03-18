package com.mortarifabio.marvelcharacterschallenge.characters

import android.content.Context
import com.mortarifabio.marvelcharacterschallenge.api.ApiService.marvelApi
import com.mortarifabio.marvelcharacterschallenge.api.ResponseApi
import com.mortarifabio.marvelcharacterschallenge.database.MarvelDatabase
import com.mortarifabio.marvelcharacterschallenge.model.Favorite
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LIMIT

class CharactersRepository(
    private val context: Context
) {

    private val favoritesDao by lazy {
        MarvelDatabase.getDatabase(context).favoritesDao()
    }

    suspend fun getCharacters(page: Int, characterName: String): Any {
        val offset = API_LIMIT * (page - 1)
        return try {
            val response = if(characterName != "") {
                marvelApi.getCharactersByName(characterName, API_LIMIT, offset)
            } else {
                marvelApi.getCharacters(API_LIMIT, offset)
            }
            if (response.isSuccessful) {
                ResponseApi.Success(response.body())
            } else {
                ResponseApi.Error(response.code(), response.toString())
            }
        } catch (e: Exception) {
            ResponseApi.Error(0, e.localizedMessage ?: e.message.toString())
        }
    }

    suspend fun loadPagedFavorites(page: Int): List<Favorite> {
        return favoritesDao.loadPagedFavorites(page)
    }

    suspend fun loadFavoritesIds(): List<Long> {
        return favoritesDao.loadFavoritesIds()
    }
}