package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CharactersData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharactersResult>,
    val total: Int
) : Parcelable
