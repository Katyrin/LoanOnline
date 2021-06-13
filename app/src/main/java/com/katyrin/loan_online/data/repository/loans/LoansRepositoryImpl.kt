package com.katyrin.loan_online.data.repository.loans

import com.katyrin.loan_online.data.datasource.cache.CacheLoansDataSource
import com.katyrin.loan_online.data.datasource.loans.LoansDataSource
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.network.NetworkStateRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val loansDataSource: LoansDataSource,
    private val networkStateRepository: NetworkStateRepository,
    private val cashLoansDataSource: CacheLoansDataSource
) : LoansRepository {

    override fun getLoans(token: String): Single<List<LoanDTO>> =
        networkStateRepository
            .isOnlineSingle()
            .flatMap { isOnline -> getLoansIsOnline(isOnline, token) }
            .subscribeOn(Schedulers.io())

    private fun getLoansIsOnline(isOnline: Boolean, token: String): Single<List<LoanDTO>> =
        if (isOnline) {
            loansDataSource
                .getLoans(token)
                .flatMap(cashLoansDataSource::putLoans)
        } else {
            cashLoansDataSource.getLoans(token)
        }

    override fun getLoanById(token: String, id: Int): Single<LoanDTO> =
        networkStateRepository
            .isOnlineSingle()
            .flatMap { isOnline -> getLoanByIdIsOnline(isOnline, token, id) }
            .subscribeOn(Schedulers.io())

    private fun getLoanByIdIsOnline(isOnline: Boolean, token: String, id: Int): Single<LoanDTO> =
        if (isOnline) {
            loansDataSource
                .getLoanById(token, id)
                .flatMap(cashLoansDataSource::putLoanById)
        } else {
            cashLoansDataSource.getLoanById(token, id)
        }
}