package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Characters(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: CharactersData,
    val etag: String,
    val status: String
) : Parcelable
