package com.mortarifabio.marvelcharacterschallenge.details.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mortarifabio.marvelcharacterschallenge.details.DetailsBusiness
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import kotlinx.coroutines.launch

class DetailsViewModel(
    application: Application
): AndroidViewModel(application) {

    private val business by lazy {
        DetailsBusiness(application)
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