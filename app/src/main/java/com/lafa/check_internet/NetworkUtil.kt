package com.lafa.check_internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkUtil {

    companion object {

//        fun networkState(context: Context): Boolean {
//            try {
//                val connectivityManager: ConnectivityManager =
//                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//                val networkInfo = connectivityManager.activeNetworkInfo
//                return (networkInfo != null && networkInfo.isConnected)
//            } catch (e: NullPointerException) {
//                e.printStackTrace()
//                return false
//            }
//        }


        fun networkState(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            try {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNet =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                return when {
                    actNet.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNet.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNet.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
                return false
            }
        }
    }
}