package com.katyrin.loan_online.data.datasource.loans

import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single

interface LoansDataSource {
    fun getLoans(): Single<List<LoanDTO>>
    fun getLoanById(id: Int): Single<LoanDTO>
}