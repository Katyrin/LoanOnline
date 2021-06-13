package com.katyrin.loan_online.data.datasource.exit

import io.reactivex.Completable

interface ExitDataSource {
    fun deleteLoansTable(): Completable
}