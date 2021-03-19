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
    val isListEmpty: MutableLiveData<Boolean> = MutableLiveData(false)
    private val business by lazy {
        FavoritesBusiness(application)
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favorites = business.loadFavorites()
            favoritesLiveData.postValue(favorites)
            isListEmpty.postValue(favorites.isEmpty())
        }
    }

    fun removeFavorite(character: CharactersResult?) {
        viewModelScope.launch {
            val totalFavorites = business.removeFavorite(character)
            isListEmpty.postValue(totalFavorites == 0)
        }
    }

}