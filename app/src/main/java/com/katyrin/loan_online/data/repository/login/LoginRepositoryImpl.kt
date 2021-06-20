package com.katyrin.loan_online.data.repository.login

import com.katyrin.loan_online.data.datasource.login.LoginDataSource
import com.katyrin.loan_online.data.datasource.session.SessionManager
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val sessionManager: SessionManager
) : LoginRepository {

    override fun postRegistration(user: User): Single<UserRegistrationDTO> =
        loginDataSource.postRegistration(user)

    override fun postLogin(user: User): Single<ResponseBody> =
        loginDataSource.postLogin(user)

    override fun saveIsRegistered(boolean: Boolean) {
        sessionManager.saveIsRegistered(boolean)
    }

    override fun getIsRegistered(): Boolean = sessionManager.getIsRegistered()

    override fun saveAuthToken(token: String?) {
        sessionManager.saveAuthToken(token)
    }
}