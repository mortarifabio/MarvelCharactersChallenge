package com.mortarifabio.marvelcharacterschallenge.extensions

import com.mortarifabio.marvelcharacterschallenge.model.Favorite
import com.mortarifabio.marvelcharacterschallenge.model.CharactersResult

fun List<Favorite>.toCharactersResultMutableList(): MutableList<CharactersResult> {
    return this.map {
        CharactersResult(
            description = it.description,
            id = it.id,
            name = it.name,
            smallImage = it.smallImage,
            largeImage = it.largeImage,
            thumbnail = null,
            favorite = true
        )
    }.toMutableList()
}