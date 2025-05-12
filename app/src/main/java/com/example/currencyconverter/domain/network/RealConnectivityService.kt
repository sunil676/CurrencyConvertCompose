package com.example.currencyconverter.domain.network

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealConnectivityService @Inject constructor(private val context: Context) : ConnectivityService {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isNetworkAvailable(): Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                trySend(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onCapabilitiesChanged(
                network: android.net.Network,
                capabilities: android.net.NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, capabilities)
                if (capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    trySend(true)
                } else {
                    trySend(false)
                }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        val initialNetwork = connectivityManager.activeNetwork
        if (initialNetwork != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(initialNetwork)
            if (capabilities != null && capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                trySend(true)
            } else {
                trySend(false)
            }
        } else {
            trySend(false)
        }
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.distinctUntilChanged()
}