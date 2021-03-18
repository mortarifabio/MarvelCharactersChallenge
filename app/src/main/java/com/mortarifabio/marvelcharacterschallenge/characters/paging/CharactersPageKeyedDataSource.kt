package com.mortarifabio.marvelcharacterschallenge.characters.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.mortarifabio.marvelcharacterschallenge.api.ResponseApi
import com.mortarifabio.marvelcharacterschallenge.characters.CharactersRepository
import com.mortarifabio.marvelcharacterschallenge.extensions.largeImage
import com.mortarifabio.marvelcharacterschallenge.extensions.smallImage
import com.mortarifabio.marvelcharacterschallenge.model.Characters
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersPageKeyedDataSource(
    private val characterName: String
) : PageKeyedDataSource<Int, CharactersResult>() {

    private val repository: CharactersRepository by lazy {
        CharactersRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharactersResult>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = repository.getCharacters(API_FIRST_PAGE, characterName)) {
                is ResponseApi.Success -> {
                    val characters = response.data as Characters
                    generateImagesPaths(characters)
                    callback.onResult(characters.data.results, null, API_FIRST_PAGE + 1)
                }
                is ResponseApi.Error -> {
                    Log.e("API Error", response.message)
                    callback.onResult(mutableListOf(), null, API_FIRST_PAGE + 1)
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
                    callback.onResult(characters.data.results, nextPage)
                }
                is ResponseApi.Error -> {
                    Log.e("API Error", response.message)
                    callback.onResult(mutableListOf(), nextPage)
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

}