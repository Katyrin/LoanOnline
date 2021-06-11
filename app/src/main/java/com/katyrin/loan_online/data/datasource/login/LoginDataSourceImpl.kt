package com.katyrin.loan_online.data.datasource.login

import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val api: Api
) : LoginDataSource {
    override fun postRegistration(user: User): Single<UserRegistrationDTO> =
        api.postRegistration(user)
            .subscribeOn(Schedulers.io())

    override fun postLogin(user: User): Single<ResponseBody> =
        api.postLogin(user)
            .subscribeOn(Schedulers.io())
}