package com.mortarifabio.marvelcharacterschallenge.api

sealed class ResponseApi {
    class Success(val data: Any?): ResponseApi()
    class Error(val message: String): ResponseApi()
}