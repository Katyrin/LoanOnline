package com.katyrin.loan_online.data.repository

import com.katyrin.loan_online.data.datasource.LoginDataSource
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {

    override fun postRegistration(user: User): Single<UserRegistrationDTO> =
        loginDataSource.postRegistration(user)

    override fun postLogin(user: User): Single<ResponseBody> =
        loginDataSource.postLogin(user)
}