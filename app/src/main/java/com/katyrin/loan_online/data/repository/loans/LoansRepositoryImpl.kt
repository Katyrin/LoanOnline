package com.katyrin.loan_online.data.repository.loans

import com.katyrin.loan_online.data.datasource.loans.LoansDataSource
import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val loansDataSource: LoansDataSource
) : LoansRepository {
    override fun getLoans(token: String): Single<List<LoanDTO>> =
        loansDataSource.getLoans(token)
}