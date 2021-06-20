package com.katyrin.loan_online.data.repository.exit

import io.reactivex.Completable

interface ExitRepository {
    fun clearAllData(): Completable
}