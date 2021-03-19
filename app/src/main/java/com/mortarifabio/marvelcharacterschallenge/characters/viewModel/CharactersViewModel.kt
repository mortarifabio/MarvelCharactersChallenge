package com.mortarifabio.marvelcharacterschallenge.characters.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.mortarifabio.marvelcharacterschallenge.characters.CharactersBusiness
import com.mortarifabio.marvelcharacterschallenge.characters.paging.CharactersDataSourceFactory
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LIMIT
import kotlinx.coroutines.launch

class CharactersViewModel(
    application: Application
): AndroidViewModel(application) {

    var charactersPagedList: LiveData<PagedList<CharactersResult>>? = null
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(API_LIMIT)
        .build()
    private val business by lazy {
        CharactersBusiness(application)
    }

    init {
        getCharacters()
    }

    fun getCharacters(characterName: String = "") {
        val charactersDataSourceFactory = CharactersDataSourceFactory(getApplication(), characterName)
        charactersPagedList = LivePagedListBuilder(charactersDataSourceFactory, pagedListConfig).build()
    }

    fun addFavorite(character: CharactersResult?) {
        viewModelScope.launch {
            business.addFavorite(character)
        }
    }

    fun removeFavorite(character: CharactersResult?) {
        viewModelScope.launch {
            business.removeFavorite(character)
        }
    }
}