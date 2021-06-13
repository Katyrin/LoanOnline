package com.katyrin.loan_online.data.datasource.cache

import com.katyrin.loan_online.data.datasource.loans.LoansDataSource
import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single

interface CacheLoansDataSource : LoansDataSource {
    fun putLoans(loans: List<LoanDTO>): Single<List<LoanDTO>>
    fun putLoanById(loan: LoanDTO): Single<LoanDTO>
}