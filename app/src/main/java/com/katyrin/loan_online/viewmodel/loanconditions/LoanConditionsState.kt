package com.katyrin.loan_online.viewmodel.loanconditions

import com.katyrin.loan_online.data.model.LoanConditionsDTO

sealed class LoanConditionsState {
    data class Success(val loanConditionsDTO: LoanConditionsDTO) : LoanConditionsState()
    object Error : LoanConditionsState()
    object Loading : LoanConditionsState()
}
