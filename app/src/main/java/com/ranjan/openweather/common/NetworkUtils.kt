package com.ranjan.openweather.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class NetworkUtils @Inject constructor(val context: Context) {
    fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isOnline =
            capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting && isOnline
    }
}