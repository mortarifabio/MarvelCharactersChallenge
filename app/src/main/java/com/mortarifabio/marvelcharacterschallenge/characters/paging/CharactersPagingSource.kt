package com.mortarifabio.marvelcharacterschallenge.characters.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mortarifabio.marvelcharacterschallenge.api.ResponseApi
import com.mortarifabio.marvelcharacterschallenge.characters.CharactersRepository
import com.mortarifabio.marvelcharacterschallenge.extensions.largeImage
import com.mortarifabio.marvelcharacterschallenge.extensions.smallImage
import com.mortarifabio.marvelcharacterschallenge.model.Characters
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_FIRST_PAGE

class CharactersPagingSource(
    private val characterName: String,
    private val context: Context
) : PagingSource<Int, CharactersResult>() {

    private val repository: CharactersRepository by lazy {
        CharactersRepository(context)
    }
    private var favoritesIds = listOf<Long>()


    override fun getRefreshKey(state: PagingState<Int, CharactersResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResult> {
        try {
            val page = params.key ?: API_FIRST_PAGE
            when (val response = repository.getCharacters(page, characterName)) {
                is ResponseApi.Success -> {
                    favoritesIds = repository.loadFavoritesIds()
                    val characters = response.data as Characters
                    generateImagesPaths(characters)
                    setupFavorites(characters, favoritesIds)
                    return LoadResult.Page(
                        data = characters.data.results.toList(),
                        prevKey = null,
                        nextKey = page + 1
                    )
                }
                is ResponseApi.Error -> {
                    return LoadResult.Error(Exception(response.message))
                }
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
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