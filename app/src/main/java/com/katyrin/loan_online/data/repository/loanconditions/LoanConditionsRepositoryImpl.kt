package com.katyrin.loan_online.data.repository.loanconditions

import com.katyrin.loan_online.data.datasource.loanconditions.LoanConditionsDataSource
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import io.reactivex.Single
import javax.inject.Inject

class LoanConditionsRepositoryImpl @Inject constructor(
    private val loanConditionsDataSource: LoanConditionsDataSource
) : LoanConditionsRepository {
    override fun getLoansCondition(): Single<LoanConditionsDTO> =
        loanConditionsDataSource.getLoansCondition()
}