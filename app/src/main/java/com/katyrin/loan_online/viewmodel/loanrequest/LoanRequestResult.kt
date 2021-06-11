package com.katyrin.loan_online.viewmodel.loanrequest

import com.katyrin.loan_online.data.model.LoanDTO

sealed class LoanRequestResult{
    data class Success(val loanDTO: LoanDTO) : LoanRequestResult()
    object Error : LoanRequestResult()
}
