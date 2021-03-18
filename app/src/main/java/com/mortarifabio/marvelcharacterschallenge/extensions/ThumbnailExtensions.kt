package com.mortarifabio.marvelcharacterschallenge.extensions

import com.mortarifabio.marvelcharacterschallenge.model.Thumbnail
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_LARGE_IMAGE_PATH
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_SMALL_IMAGE_PATH

fun Thumbnail.smallImage() : String {
    return "${this.path.replace("http", "https")}/$API_SMALL_IMAGE_PATH.${this.extension}"
}

fun Thumbnail.largeImage() : String {
    return "${this.path.replace("http", "https")}/$API_LARGE_IMAGE_PATH.${this.extension}"
}