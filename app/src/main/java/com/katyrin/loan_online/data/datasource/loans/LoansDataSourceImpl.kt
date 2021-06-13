package com.katyrin.loan_online.data.datasource.loans

import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.data.model.LoanDTO
import io.reactivex.Single
import javax.inject.Inject

class LoansDataSourceImpl @Inject constructor(
    private val api: Api
) : LoansDataSource {

    override fun getLoans(token: String): Single<List<LoanDTO>> = api.getLoans(token)

    override fun getLoanById(token: String, id: Int): Single<LoanDTO> = api.getLoanById(token, id)
}