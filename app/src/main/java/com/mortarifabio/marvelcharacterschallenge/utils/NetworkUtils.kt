package com.mortarifabio.marvelcharacterschallenge.utils

import android.content.Context
import android.net.*
import android.os.Build

class NetworkUtils(
    val context: Context
) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    fun setupNetworkObserver(showAlert: (Boolean) -> Unit) {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                showAlert(false)
            }
            override fun onLost(network: Network) {
                showAlert(true)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager?.registerNetworkCallback(request, networkCallback)
        }
    }

    fun isNetworkConnected() : Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val activeNetwork = connectivityManager?.activeNetwork
            activeNetwork?.let {
                true
            } ?: false
        } else {
            val activeNetwork = connectivityManager?.activeNetworkInfo
            activeNetwork?.isConnected == true
        }
    }
}