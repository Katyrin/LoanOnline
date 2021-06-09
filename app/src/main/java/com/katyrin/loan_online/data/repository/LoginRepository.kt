package com.katyrin.loan_online.data.repository

import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import okhttp3.ResponseBody

interface LoginRepository {
    fun postRegistration(user: User) : Single<UserRegistrationDTO>
    fun postLogin(user: User) : Single<ResponseBody>
}