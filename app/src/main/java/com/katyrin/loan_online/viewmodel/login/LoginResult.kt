package com.katyrin.loan_online.viewmodel.login

sealed class LoginResult {
    data class Success(val token: String?) : LoginResult()
    object Error : LoginResult()
}