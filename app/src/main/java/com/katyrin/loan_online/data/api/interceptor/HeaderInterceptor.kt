package com.katyrin.loan_online.data.api.interceptor

import com.katyrin.loan_online.data.datasource.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(HEADER_ACCEPT, ACCEPT_VALUE)
                .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .also { requestBuilder ->
                    sessionManager.fetchAuthToken()
                        ?.let { requestBuilder.addHeader(HEADER_AUTHORIZATION, it) }
                }
                .build()
        )
    }

    private companion object {
        const val HEADER_ACCEPT = "accept"
        const val ACCEPT_VALUE = "*/*"
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val HEADER_AUTHORIZATION = "Authorization"
    }
}