package com.katyrin.loan_online.data.datasource.loanconditions

import com.katyrin.loan_online.data.model.LoanConditionsDTO
import io.reactivex.Single

interface LoanConditionsDataSource {
    fun getLoansCondition(token: String): Single<LoanConditionsDTO>
}