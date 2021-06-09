package com.katyrin.loan_online.di

import com.katyrin.loan_online.data.datasource.LoginDataSource
import com.katyrin.loan_online.data.datasource.LoginDataSourceImpl
import com.katyrin.loan_online.data.repository.LoginRepository
import com.katyrin.loan_online.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun loginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource

    @Binds
    @Singleton
    fun loginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}