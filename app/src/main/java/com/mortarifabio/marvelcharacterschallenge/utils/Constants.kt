package com.mortarifabio.marvelcharacterschallenge.utils

class Constants {
    object Api {
        const val API_BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        const val API_TIMESTAMP_LABEL = "ts"
        const val API_KEY_LABEL = "apikey"
        const val API_HASH_LABEL = "hash"
        const val API_ORDER_LABEL = "orderBy"
        const val API_ORDER = "name"
        const val API_LIMIT = 50
        const val API_FIRST_PAGE = 1
        const val API_SMALL_IMAGE_PATH = "standard_xlarge"
        const val API_LARGE_IMAGE_PATH = "standard_incredible"
    }
}