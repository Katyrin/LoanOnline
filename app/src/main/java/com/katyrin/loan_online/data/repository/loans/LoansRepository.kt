package com.katyrin.loan_online.data.repository.loans

import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single

interface LoansRepository {
    fun getLoans(): Single<List<LoanDTO>>
    fun getLoanById(id: Int): Single<LoanDTO>
}