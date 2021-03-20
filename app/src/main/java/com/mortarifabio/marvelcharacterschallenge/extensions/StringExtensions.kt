package com.mortarifabio.marvelcharacterschallenge.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mortarifabio.marvelcharacterschallenge.R

fun String.showInSnackBar(view: View) {
    val snackbar = Snackbar.make(view, this, Snackbar.LENGTH_INDEFINITE)
    snackbar.apply {
        setAction(R.string.close){
            dismiss()
        }
        show()
    }
}