package com.mortarifabio.marvelcharacterschallenge.api

import com.mortarifabio.marvelcharacterschallenge.model.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
    ): Response<Characters>

    @GET("characters")
    suspend fun getCharactersByName(
            @Query("nameStartsWith") name: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
    ): Response<Characters>
}