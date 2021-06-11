package com.katyrin.loan_online.data.repository.loans

import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single

interface LoansRepository {
    fun getLoans(token: String): Single<List<LoanDTO>>
}