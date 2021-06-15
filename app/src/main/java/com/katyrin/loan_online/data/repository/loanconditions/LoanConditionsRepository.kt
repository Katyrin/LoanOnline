package com.katyrin.loan_online.data.repository.loanconditions

import com.katyrin.loan_online.data.model.LoanConditionsDTO
import io.reactivex.Single

interface LoanConditionsRepository {
    fun getLoansCondition(): Single<LoanConditionsDTO>
}