package com.mortarifabio.marvelcharacterschallenge.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Thumbnail(
    val extension: String,
    val path: String
) : Parcelable
