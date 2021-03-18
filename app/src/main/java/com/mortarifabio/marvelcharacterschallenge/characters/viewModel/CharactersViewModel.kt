package com.mortarifabio.marvelcharacterschallenge.characters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.mortarifabio.marvelcharacterschallenge.characters.paging.CharactersDataSourceFactory
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LIMIT

class CharactersViewModel : ViewModel() {
    var charactersPagedList: LiveData<PagedList<CharactersResult>>? = null
    private var charactersLiveDataSource: LiveData<PageKeyedDataSource<Int, CharactersResult>>? = null
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(API_LIMIT).build()

    init {
        getCharacters()
    }

    fun getCharacters(characterName: String = "") {
        val charactersDataSourceFactory = CharactersDataSourceFactory(characterName)
        charactersLiveDataSource = charactersDataSourceFactory.getLiveDataSource()
        charactersPagedList = LivePagedListBuilder(charactersDataSourceFactory, pagedListConfig).build()
    }
}