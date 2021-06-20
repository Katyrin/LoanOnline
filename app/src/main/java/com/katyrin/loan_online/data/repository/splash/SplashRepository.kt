package com.katyrin.loan_online.data.repository.splash

interface SplashRepository {
    fun fetchAuthToken(): String?
}