package com.katyrin.loan_online.data.datasource.cache

import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanEntity
import com.katyrin.loan_online.data.model.LoanState

object LoanEntityConverter {

    fun convertLoanEntitiesToLoansDTO(loanEntities: List<LoanEntity>): List<LoanDTO> =
        loanEntities.map { convertLoanEntityToLoanDTO(it) }

    fun convertLoanEntityToLoanDTO(loanEntity: LoanEntity): LoanDTO =
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

    fun convertLoanDTOToLoanEntity(loanDTO: LoanDTO): LoanEntity =
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