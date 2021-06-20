package com.katyrin.loan_online.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katyrin.loan_online.data.api.Api
import com.katyrin.loan_online.data.api.interceptor.HeaderInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface ApiModule {
    companion object {

        private const val BASE_URL = "http://103.23.208.205:8082/"

        @Provides
        @Singleton
        fun apiPost(gson: Gson, client: OkHttpClient): Api = Retrofit.Builder()
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
        fun clientPost(interceptor: Interceptor): OkHttpClient =
            OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                addInterceptor(interceptor)
            }.build()
    }

    @Binds
    @Singleton
    fun postInterceptor(headerInterceptor: HeaderInterceptor): Interceptor
}