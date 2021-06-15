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

    override fun getLoans(): Single<List<LoanDTO>> =
        networkStateRepository
            .isOnlineSingle()
            .flatMap(::getLoansIsOnline)
            .subscribeOn(Schedulers.io())

    private fun getLoansIsOnline(isOnline: Boolean): Single<List<LoanDTO>> =
        if (isOnline) {
            loansDataSource
                .getLoans()
                .flatMap(cashLoansDataSource::putLoans)
        } else {
            cashLoansDataSource.getLoans()
        }

    override fun getLoanById(id: Int): Single<LoanDTO> =
        networkStateRepository
            .isOnlineSingle()
            .flatMap { isOnline -> getLoanByIdIsOnline(isOnline, id) }
            .subscribeOn(Schedulers.io())

    private fun getLoanByIdIsOnline(isOnline: Boolean, id: Int): Single<LoanDTO> =
        if (isOnline) {
            loansDataSource
                .getLoanById(id)
                .flatMap(cashLoansDataSource::putLoanById)
        } else {
            cashLoansDataSource.getLoanById(id)
        }
}