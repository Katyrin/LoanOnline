package com.katyrin.loan_online.data.api.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    context: Context
) : Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .also { requestBuilder ->
                    sessionManager.fetchAuthToken()
                        ?.let { requestBuilder.addHeader("Authorization", it) }
                }
                .build()
        )
    }
}