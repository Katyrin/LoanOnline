package com.katyrin.loan_online.data.datasource.session

interface SessionManager {
    fun saveAuthToken(token: String?)
    fun saveIsRegistered(boolean: Boolean)
    fun getIsRegistered(): Boolean
    fun fetchAuthToken(): String?
}