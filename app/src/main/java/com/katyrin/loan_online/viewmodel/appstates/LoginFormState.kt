package com.katyrin.loan_online.viewmodel.appstates

sealed class LoginFormState {
    object ErrorUserName : LoginFormState()
    object ErrorPassword : LoginFormState()
    object Success : LoginFormState()
}