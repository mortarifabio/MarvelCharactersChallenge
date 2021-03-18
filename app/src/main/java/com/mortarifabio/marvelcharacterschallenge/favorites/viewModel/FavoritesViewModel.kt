package com.mortarifabio.marvelcharacterschallenge.favorites.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mortarifabio.marvelcharacterschallenge.favorites.FavoritesBusiness
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import kotlinx.coroutines.launch

class FavoritesViewModel(
    application: Application
): AndroidViewModel(application) {

    val favoritesLiveData: MutableLiveData<MutableList<CharactersResult>> = MutableLiveData()
    private val business by lazy {
        FavoritesBusiness(application)
    }

    fun loadFavorites() {
        viewModelScope.launch {
            favoritesLiveData.postValue(business.loadFavorites())
        }
    }

    fun removeFavorite(character: CharactersResult?) {
        viewModelScope.launch {
            business.removeFavorite(character)
        }
    }

}