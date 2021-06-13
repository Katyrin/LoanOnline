package com.katyrin.loan_online.viewmodel.login

sealed class LoginResult {
    data class SuccessRegistration(val token: String?) : LoginResult()
    data class SuccessLogin(val token: String?) : LoginResult()
    object Error : LoginResult()
}