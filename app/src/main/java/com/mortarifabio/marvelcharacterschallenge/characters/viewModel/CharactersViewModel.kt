package com.mortarifabio.marvelcharacterschallenge.characters.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.mortarifabio.marvelcharacterschallenge.characters.CharactersBusiness
import com.mortarifabio.marvelcharacterschallenge.characters.paging.CharactersPagingSource
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LIMIT
import kotlinx.coroutines.launch

class CharactersViewModel(
    application: Application
): AndroidViewModel(application) {

    private val business by lazy {
        CharactersBusiness(application)
    }
    var name = ""
    val flow = Pager(PagingConfig(API_LIMIT)) {
        CharactersPagingSource(name, getApplication())
    }.flow.cachedIn(viewModelScope)

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