package com.mortarifabio.marvelcharacterschallenge.api

import java.io.IOException

class NoConnectionException: IOException() {
    override fun getLocalizedMessage(): String {
        return "No internet connection"
    }
}