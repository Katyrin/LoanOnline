package com.katyrin.loan_online.data.repository.network

import io.reactivex.Observable
import io.reactivex.Single

interface NetworkStateRepository {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}