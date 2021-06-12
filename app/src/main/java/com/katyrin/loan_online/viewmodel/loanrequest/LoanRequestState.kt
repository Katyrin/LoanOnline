package com.katyrin.loan_online.viewmodel.loanrequest

import com.katyrin.loan_online.data.model.LoanDTO

sealed class LoanRequestState {
    data class Success(val loanDTO: LoanDTO) : LoanRequestState()
    object Loading : LoanRequestState()
    object Error : LoanRequestState()
}
