package com.katyrin.loan_online.data.repository.loanrequest

import com.katyrin.loan_online.data.datasource.loanrequest.LoanRequestDataSource
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import io.reactivex.Single
import javax.inject.Inject

class LoanRequestRepositoryImpl @Inject constructor(
    private val loanRequestDataSource: LoanRequestDataSource
) : LoanRequestRepository {
    override fun postLoansRequest(loanRequest: LoanRequest): Single<LoanDTO> =
        loanRequestDataSource.postLoansRequest(loanRequest)
}