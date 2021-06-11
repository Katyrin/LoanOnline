package com.katyrin.loan_online.data.repository.loanrequest

import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import io.reactivex.Single

interface LoanRequestRepository {
    fun postLoansRequest(token: String, loanRequest: LoanRequest): Single<LoanDTO>
}