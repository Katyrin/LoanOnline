package com.katyrin.loan_online.data.datasource.exit

import com.katyrin.loan_online.data.storage.LoansDataBase
import io.reactivex.Completable
import javax.inject.Inject

class ExitDataSourceImpl @Inject constructor(
    private val database: LoansDataBase
) : ExitDataSource {
    override fun deleteLoansTable(): Completable = database.loansDao().deleteLoansTable()
}