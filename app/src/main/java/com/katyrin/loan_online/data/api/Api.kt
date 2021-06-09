package com.katyrin.loan_online.data.api

import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.model.UserRegistrationDTO
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @Headers(value = ["accept: */*", "Content-Type: application/json"])
    @POST("registration")
    fun postRegistration(
        @Body body: User
    ): Single<UserRegistrationDTO>

    @Headers(value = ["accept: */*", "Content-Type: application/json"])
    @POST("login")
    fun postLogin(
        @Body body: User
    ): Single<ResponseBody>
}