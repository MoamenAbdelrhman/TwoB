package com.example.twob.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

interface ConnectivityObserver {

    val isConnected: Flow<Boolean>
}

class ConnectivityObserverImpl(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    override val isConnected: Flow<Boolean> =
        callbackFlow {

            fun isCurrentlyConnected(): Boolean {
                val network =
                    connectivityManager.activeNetwork
                        ?: return false

                val capabilities =
                    connectivityManager.getNetworkCapabilities(
                        network
                    ) ?: return false

                return capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) &&
                        capabilities.hasCapability(
                            NetworkCapabilities.NET_CAPABILITY_VALIDATED
                        )
            }

            trySend(isCurrentlyConnected())

            val callback =
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(
                        network: Network
                    ) {
                        trySend(true)
                    }

                    override fun onLost(
                        network: Network
                    ) {
                        trySend(
                            isCurrentlyConnected()
                        )
                    }
                }

            connectivityManager.registerDefaultNetworkCallback(
                callback
            )

            awaitClose {
                connectivityManager.unregisterNetworkCallback(
                    callback
                )
            }
        }
            .distinctUntilChanged()
}