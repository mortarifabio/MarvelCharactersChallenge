package com.mortarifabio.marvelcharacterschallenge.characters

import com.mortarifabio.marvelcharacterschallenge.api.ApiService.marvelApi
import com.mortarifabio.marvelcharacterschallenge.api.ResponseApi
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LIMIT

class CharactersRepository {
    suspend fun getCharacters(page: Int, characterName: String): Any {
        val offset = API_LIMIT * (page - 1)
        return try {
            val response = if(characterName != "") {
                marvelApi.getCharactersByName(characterName, API_LIMIT, offset)
            } else {
                marvelApi.getCharacters(API_LIMIT, offset)
            }
            if (response.isSuccessful) {
                ResponseApi.Success(response.body())
            } else {
                ResponseApi.Error(response.toString())
            }
        } catch (e: Exception) {
            ResponseApi.Error(e.localizedMessage ?: e.toString())
        }
    }
}