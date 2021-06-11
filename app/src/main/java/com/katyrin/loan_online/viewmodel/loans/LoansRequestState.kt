package com.katyrin.loan_online.viewmodel.loans

import com.katyrin.loan_online.data.model.LoanDTO

sealed class LoansRequestState {
    data class Success(val loans: List<LoanDTO>) : LoansRequestState()
    object Error : LoansRequestState()
    object Loading : LoansRequestState()
}