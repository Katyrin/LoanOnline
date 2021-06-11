package com.katyrin.loan_online.data.datasource.loanrequest

import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanRequestDataSourceImpl @Inject constructor(
    private val api: Api
) : LoanRequestDataSource {
    override fun postLoansRequest(token: String, loanRequest: LoanRequest): Single<LoanDTO> =
        api.postLoansRequest(token, loanRequest)
            .subscribeOn(Schedulers.io())
}