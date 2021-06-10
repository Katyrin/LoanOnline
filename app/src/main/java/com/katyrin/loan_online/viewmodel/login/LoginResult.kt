package com.katyrin.loan_online.viewmodel.login

sealed class LoginResult {
    data class Success(val string: String?) : LoginResult()
    object Error : LoginResult()
}