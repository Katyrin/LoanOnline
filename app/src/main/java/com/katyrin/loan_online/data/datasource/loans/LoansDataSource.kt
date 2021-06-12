package com.katyrin.loan_online.data.datasource.loans

import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single

interface LoansDataSource {
    fun getLoans(token: String): Single<List<LoanDTO>>
    fun getLoanFromId(token: String, id: Int): Single<LoanDTO>
}