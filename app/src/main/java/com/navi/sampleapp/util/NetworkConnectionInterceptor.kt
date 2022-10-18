package com.navi.sampleapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.navi.sampleapp.R
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context = context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected(mContext)) {
            throw NoConnectionException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(context: Context): Boolean {
        var isConnected = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        isConnected = true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        isConnected = true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        isConnected = true
                    }
                }
            }
        } else {
            cm?.run {
                val netInfo = this.activeNetworkInfo
                isConnected = netInfo != null && netInfo.isConnected
            }
        }
        return isConnected
    }

    inner class NoConnectionException : IOException() {
        override val message: String
            get() = mContext.getString(R.string.no_internet)
    }
}