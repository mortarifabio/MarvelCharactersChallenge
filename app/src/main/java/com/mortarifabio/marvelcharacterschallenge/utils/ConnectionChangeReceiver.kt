package com.mortarifabio.marvelcharacterschallenge.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast


class ConnectionChangeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val connectivityManager = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            val mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (activeNetInfo != null) {
                Toast.makeText(
                    it,
                    "Active Network Type : " + activeNetInfo.typeName,
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (mobNetInfo != null) {
                Toast.makeText(
                    it,
                    "Mobile Network Type : " + mobNetInfo.typeName,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}