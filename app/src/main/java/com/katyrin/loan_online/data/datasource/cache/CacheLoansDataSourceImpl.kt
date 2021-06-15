package com.katyrin.loan_online.data.datasource.cache

import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanEntity
import com.katyrin.loan_online.data.model.LoanState
import com.katyrin.loan_online.data.storage.LoansDao
import com.katyrin.loan_online.data.storage.LoansDataBase
import com.katyrin.loan_online.utils.LOAN_INFORMATION_NOT_FOUND
import io.reactivex.Single
import javax.inject.Inject

class CacheLoansDataSourceImpl @Inject constructor(
    database: LoansDataBase
) : CacheLoansDataSource {

    private val loansDao: LoansDao = database.loansDao()

    override fun getLoans(): Single<List<LoanDTO>> =
        loansDao
            .getLoans()
            .map(::convertLoanEntitiesToLoansDTO)

    override fun getLoanById(id: Int): Single<LoanDTO> =
        loansDao
            .getLoanById(id)
            .onErrorResumeNext(Single.error(RuntimeException(LOAN_INFORMATION_NOT_FOUND)))
            .map(::convertLoanEntityToLoanDTO)

    override fun putLoans(loans: List<LoanDTO>): Single<List<LoanDTO>> =
        loansDao
            .putLoans(loans.map { convertLoanDTOToLoanEntity(it) })
            .andThen(getLoans())

    override fun putLoanById(loan: LoanDTO): Single<LoanDTO> =
        loansDao
            .putLoanById(convertLoanDTOToLoanEntity(loan))
            .andThen(getLoanById(loan.id))


    private fun convertLoanEntitiesToLoansDTO(loanEntities: List<LoanEntity>): List<LoanDTO> =
        loanEntities.map { convertLoanEntityToLoanDTO(it) }

    private fun convertLoanEntityToLoanDTO(loanEntity: LoanEntity): LoanDTO =
        LoanDTO(
            loanEntity.amount,
            loanEntity.date,
            loanEntity.firstName,
            loanEntity.id,
            loanEntity.lastName,
            loanEntity.percent,
            loanEntity.period,
            loanEntity.phoneNumber,
            getLoanStateFromString(loanEntity.state)
        )

    private fun getLoanStateFromString(string: String): LoanState =
        when (string) {
            LoanState.APPROVED.name -> LoanState.APPROVED
            LoanState.REJECTED.name -> LoanState.REJECTED
            else -> LoanState.REGISTERED
        }

    private fun convertLoanDTOToLoanEntity(loanDTO: LoanDTO): LoanEntity =
        LoanEntity(
            loanDTO.amount,
            loanDTO.date,
            loanDTO.firstName,
            loanDTO.id,
            loanDTO.lastName,
            loanDTO.percent,
            loanDTO.period,
            loanDTO.phoneNumber,
            loanDTO.state.name
        )
}