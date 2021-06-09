package com.katyrin.loan_online.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun api(gson: Gson, client: OkHttpClient): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build().create(Api::class.java)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun client(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
}