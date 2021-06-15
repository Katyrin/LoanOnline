package com.katyrin.loan_online.data.api

import com.katyrin.loan_online.data.model.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("registration")
    fun postRegistration(@Body body: User): Single<UserRegistrationDTO>

    @POST("login")
    fun postLogin(@Body body: User): Single<ResponseBody>

    @POST("loans")
    fun postLoansRequest(@Body body: LoanRequest): Single<LoanDTO>

    @GET("loans/conditions")
    fun getLoansCondition(): Single<LoanConditionsDTO>

    @GET("loans/all")
    fun getLoans(): Single<List<LoanDTO>>

    @GET("loans/{id}")
    fun getLoanById(@Path(value = "id", encoded = true) id: Int): Single<LoanDTO>
}