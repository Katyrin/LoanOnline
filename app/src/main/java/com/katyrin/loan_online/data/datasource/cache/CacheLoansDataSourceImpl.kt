package com.katyrin.loan_online.data.datasource.cache

import com.katyrin.loan_online.data.datasource.cache.LoanEntityConverter.convertLoanDTOToLoanEntity
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.storage.LoansDao
import com.katyrin.loan_online.data.storage.LoansDataBase
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

class CacheLoansDataSourceImpl @Inject constructor(
    database: LoansDataBase
) : CacheLoansDataSource {

    private val loansDao: LoansDao = database.loansDao()

    override fun getLoans(): Single<List<LoanDTO>> =
        loansDao
            .getLoans()
            .map(LoanEntityConverter::convertLoanEntitiesToLoansDTO)

    override fun getLoanById(id: Int): Single<LoanDTO> =
        loansDao
            .getLoanById(id)
            .onErrorResumeNext(Single.error(IOException(LOAN_INFORMATION_NOT_FOUND)))
            .map(LoanEntityConverter::convertLoanEntityToLoanDTO)

    override fun putLoans(loans: List<LoanDTO>): Single<List<LoanDTO>> =
        loansDao
            .putLoans(loans.map { convertLoanDTOToLoanEntity(it) })
            .andThen(getLoans())

    override fun putLoanById(loan: LoanDTO): Single<LoanDTO> =
        loansDao
            .putLoanById(convertLoanDTOToLoanEntity(loan))
            .andThen(getLoanById(loan.id))

    private companion object {
        const val LOAN_INFORMATION_NOT_FOUND = "Loan information not found"
    }
}