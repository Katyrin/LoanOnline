package com.katyrin.loan_online.viewmodel.login

sealed class LoginResult {
    data class Success(val loggedInUserView: LoggedInUserView) : LoginResult()
    object Error : LoginResult()
}