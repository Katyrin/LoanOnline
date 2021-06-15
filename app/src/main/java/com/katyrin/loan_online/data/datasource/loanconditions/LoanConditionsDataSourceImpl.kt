package com.katyrin.loan_online.data.datasource.loanconditions

import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanConditionsDataSourceImpl @Inject constructor(
    private val api: Api
) : LoanConditionsDataSource {
    override fun getLoansCondition(): Single<LoanConditionsDTO> =
        api.getLoansCondition()
            .subscribeOn(Schedulers.io())
}