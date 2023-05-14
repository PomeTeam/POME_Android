package com.teampome.pome

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teampome.pome.databinding.PomeRemoveDialogBinding

class PomeConnectivityReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            ConnectivityUtils.notifyNetworkStatus(it)
        }
    }

    enum class NetworkState(val isConnected: Boolean) {
        CONNECTED(true),
        DISCONNECTED(false)
    }

    object ConnectivityUtils {
        private val liveConnectivityState = MutableLiveData<NetworkState>()

        fun notifyNetworkStatus(context: Context) {
            val newState = getLatestConnectivityStatusWithContext(context)

            liveConnectivityState.value = newState
        }

        private fun getLatestConnectivityStatusWithContext(context: Context): NetworkState {
            val isConnect = isConnected(context)

            return if(isConnect) {
                NetworkState.CONNECTED
            } else {
                NetworkState.DISCONNECTED
            }
        }

        fun getLiveConnectivityState() : LiveData<NetworkState> {
            return liveConnectivityState
        }

        private fun isConnected(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }
    }
}