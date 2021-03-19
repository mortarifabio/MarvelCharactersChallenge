package com.mortarifabio.marvelcharacterschallenge.characters.paging

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.mortarifabio.marvelcharacterschallenge.R
import com.mortarifabio.marvelcharacterschallenge.api.ResponseApi
import com.mortarifabio.marvelcharacterschallenge.characters.CharactersRepository
import com.mortarifabio.marvelcharacterschallenge.database.MarvelDatabase
import com.mortarifabio.marvelcharacterschallenge.extensions.largeImage
import com.mortarifabio.marvelcharacterschallenge.extensions.showInSnackBar
import com.mortarifabio.marvelcharacterschallenge.extensions.smallImage
import com.mortarifabio.marvelcharacterschallenge.extensions.toCharactersResultMutableList
import com.mortarifabio.marvelcharacterschallenge.model.Characters
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val context: Context,
    private val characterName: String
) : PageKeyedDataSource<Int, CharactersResult>() {

    private val repository: CharactersRepository by lazy {
        CharactersRepository(context)
    }
    private var favoritesIds = listOf<Long>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharactersResult>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getCharacters(API_FIRST_PAGE, characterName)) {
                is ResponseApi.Success -> {
                    favoritesIds = repository.loadFavoritesIds()
                    val characters = response.data as Characters
                    generateImagesPaths(characters)
                    setupFavorites(characters, favoritesIds)
                    callback.onResult(characters.data.results, null, API_FIRST_PAGE + 1)
                }
                is ResponseApi.Error -> {
                    val characters = repository.loadPagedFavorites(API_FIRST_PAGE).toCharactersResultMutableList()
                    callback.onResult(characters, null, API_FIRST_PAGE + 1)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharactersResult>) {
        loadData(params.key, params.key - 1, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharactersResult>) {
        loadData(params.key, params.key + 1, callback)
    }

    private fun loadData(page: Int, nextPage: Int, callback: LoadCallback<Int, CharactersResult>) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getCharacters(page, characterName)) {
                is ResponseApi.Success -> {
                    val characters = response.data as Characters
                    generateImagesPaths(characters)
                    setupFavorites(characters, favoritesIds)
                    callback.onResult(characters.data.results, nextPage)
                }
                is ResponseApi.Error -> {
                    val characters = repository.loadPagedFavorites(page).toCharactersResultMutableList()
                    callback.onResult(characters, nextPage)
                }
            }
        }
    }

    private fun generateImagesPaths(characters: Characters): Characters {
        characters.data.results.forEach {
            it.smallImage = it.thumbnail?.smallImage()
            it.largeImage = it.thumbnail?.largeImage()
        }
        return characters
    }

    private fun setupFavorites(characters: Characters, favorites: List<Long>): Characters {
        characters.data.results.forEach {
            it.favorite = favorites.contains(it.id)
        }
        return characters
    }
}