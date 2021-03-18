package com.mortarifabio.marvelcharacterschallenge.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(
    private val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(context)) {
            throw NoConnectionException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                networkCapabilities.hasTransport(TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}