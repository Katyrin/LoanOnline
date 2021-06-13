package com.katyrin.loan_online.data.datasource.login

import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import okhttp3.ResponseBody

interface LoginDataSource {
    fun postRegistration(user: User): Single<UserRegistrationDTO>
    fun postLogin(user: User): Single<ResponseBody>
}