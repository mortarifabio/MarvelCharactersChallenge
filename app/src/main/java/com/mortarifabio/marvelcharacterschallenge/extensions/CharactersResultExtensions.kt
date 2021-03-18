package com.mortarifabio.marvelcharacterschallenge.extensions

import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult
import com.mortarifabio.marvelcharacterschallenge.model.Favorite

fun CharactersResult.toFavorite(): Favorite {
    return Favorite(
        id = this.id,
        description = this.description,
        name = this.name,
        smallImage = this.smallImage,
        largeImage = this.largeImage
    )
}