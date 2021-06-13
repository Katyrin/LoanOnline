package com.katyrin.loan_online.data.repository.exit

import com.katyrin.loan_online.data.datasource.exit.ExitDataSource
import io.reactivex.Completable
import javax.inject.Inject

class ExitRepositoryImpl @Inject constructor(
    private val exitDataSource: ExitDataSource
): ExitRepository {
    override fun deleteLoansTable(): Completable = exitDataSource.deleteLoansTable()
}