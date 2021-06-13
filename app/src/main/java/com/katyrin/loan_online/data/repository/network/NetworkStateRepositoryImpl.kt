package com.katyrin.loan_online.data.repository.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class NetworkStateRepositoryImpl @Inject constructor(
    context: Context
) : NetworkStateRepository {

    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) = statusSubject.onNext(true)
        override fun onUnavailable() = statusSubject.onNext(false)
        override fun onLost(network: Network) = statusSubject.onNext(false)
    }

    init {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    override fun isOnline(): Observable<Boolean> = statusSubject

    override fun isOnlineSingle(): Single<Boolean> = statusSubject.first(false)
}