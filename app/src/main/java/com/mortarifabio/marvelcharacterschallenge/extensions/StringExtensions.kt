package com.mortarifabio.marvelcharacterschallenge.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mortarifabio.marvelcharacterschallenge.R

fun String.showInSnackBar(view: View): Snackbar {
    val snackbar = Snackbar.make(view, this, Snackbar.LENGTH_INDEFINITE)
    snackbar.apply {
        show()
    }
    return snackbar
}