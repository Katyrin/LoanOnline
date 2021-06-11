package com.katyrin.loan_online.viewmodel.login

import com.katyrin.loan_online.data.model.User

sealed class LoginResult {
    data class Success(val token: String?, val user: User?) : LoginResult()
    object Error : LoginResult()
}