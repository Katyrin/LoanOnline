package com.katyrin.loan_online.data.repository.splash

import com.katyrin.loan_online.data.datasource.session.SessionManager
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager
): SplashRepository {
    override fun fetchAuthToken(): String? = sessionManager.fetchAuthToken()
}