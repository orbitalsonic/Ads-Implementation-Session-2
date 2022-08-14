package com.orbitalsonic.adsimplementationsession.helpers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


object GeneralUtils {

    const val AD_TAG = "AdsInformation"
    const val REMOTE_CONFIG_TAG = "RemoteConfigTesting"

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}