package com.example.dfs.utils

import android.content.Context
import android.net.ConnectivityManager

class Utility {
    companion object {
        @JvmStatic
        fun isConnected(context: Context): Boolean {
            val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val netinfo = cm.activeNetworkInfo
            return if (netinfo != null && netinfo.isConnectedOrConnecting) {
                val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                val mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                mobile != null && mobile.isConnectedOrConnecting || wifi != null && wifi.isConnectedOrConnecting
            } else false
        }
    }

}