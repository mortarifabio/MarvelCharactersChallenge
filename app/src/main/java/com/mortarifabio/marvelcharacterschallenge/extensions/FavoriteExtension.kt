package com.mortarifabio.marvelcharacterschallenge.extensions

import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.model.Favorite

fun Favorite.toCharactersResult(): CharactersResult {
    return CharactersResult(
        description = this.description,
        id = this.id,
        name = this.name,
        smallImage = this.smallImage,
        largeImage = this.largeImage,
        thumbnail = null,
        favorite = true
    )
}