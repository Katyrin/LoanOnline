package com.katyrin.loan_online.data.datasource.loanrequest

import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import io.reactivex.Single

interface LoanRequestDataSource {
    fun postLoansRequest(token: String, loanRequest: LoanRequest): Single<LoanDTO>
}