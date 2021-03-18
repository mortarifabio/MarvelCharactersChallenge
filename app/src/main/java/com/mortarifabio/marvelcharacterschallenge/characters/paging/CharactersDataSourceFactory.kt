package com.mortarifabio.marvelcharacterschallenge.characters.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

class CharactersDataSourceFactory(
    private val characterName: String
) : DataSource.Factory<Int, CharactersResult>() {
    private val charactersLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, CharactersResult>>()

    override fun create(): DataSource<Int, CharactersResult> {
        val charactersDataSource = CharactersPageKeyedDataSource(characterName)
        charactersLiveDataSource.postValue(charactersDataSource)
        return charactersDataSource
    }

    fun getLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, CharactersResult>> {
        return charactersLiveDataSource
    }
}