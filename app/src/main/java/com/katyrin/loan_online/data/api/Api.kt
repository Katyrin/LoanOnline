package com.katyrin.loan_online.data.api

import com.katyrin.loan_online.data.model.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

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

    @Headers(value = ["accept: */*", "Content-Type: application/json"])
    @POST("loans")
    fun postLoansRequest(
        @Header("Authorization") token: String,
        @Body body: LoanRequest
    ): Single<LoanDTO>

    @Headers(value = ["accept: */*"])
    @GET("loans/conditions")
    fun getLoansCondition(
        @Header("Authorization") token: String
    ): Single<LoanConditionsDTO>

    @Headers(value = ["accept: */*"])
    @GET("loans/all")
    fun getLoans(
        @Header("Authorization") token: String
    ): Single<List<LoanDTO>>
}