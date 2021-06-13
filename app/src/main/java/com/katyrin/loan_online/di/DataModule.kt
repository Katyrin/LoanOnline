package com.katyrin.loan_online.di

import com.katyrin.loan_online.data.datasource.cache.CacheLoansDataSource
import com.katyrin.loan_online.data.datasource.cache.CacheLoansDataSourceImpl
import com.katyrin.loan_online.data.datasource.loanconditions.LoanConditionsDataSource
import com.katyrin.loan_online.data.datasource.loanconditions.LoanConditionsDataSourceImpl
import com.katyrin.loan_online.data.datasource.loanrequest.LoanRequestDataSource
import com.katyrin.loan_online.data.datasource.loanrequest.LoanRequestDataSourceImpl
import com.katyrin.loan_online.data.datasource.loans.LoansDataSource
import com.katyrin.loan_online.data.datasource.loans.LoansDataSourceImpl
import com.katyrin.loan_online.data.datasource.login.LoginDataSource
import com.katyrin.loan_online.data.datasource.login.LoginDataSourceImpl
import com.katyrin.loan_online.data.repository.loanconditions.LoanConditionsRepository
import com.katyrin.loan_online.data.repository.loanconditions.LoanConditionsRepositoryImpl
import com.katyrin.loan_online.data.repository.loanrequest.LoanRequestRepository
import com.katyrin.loan_online.data.repository.loanrequest.LoanRequestRepositoryImpl
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import com.katyrin.loan_online.data.repository.loans.LoansRepositoryImpl
import com.katyrin.loan_online.data.repository.login.LoginRepository
import com.katyrin.loan_online.data.repository.login.LoginRepositoryImpl
import com.katyrin.loan_online.data.repository.network.NetworkStateRepository
import com.katyrin.loan_online.data.repository.network.NetworkStateRepositoryImpl
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

    @Binds
    @Singleton
    fun loanRequestDataSource(
        loanRequestDataSourceImpl: LoanRequestDataSourceImpl
    ): LoanRequestDataSource

    @Binds
    @Singleton
    fun loanRequestRepository(
        loanRequestRepositoryImpl: LoanRequestRepositoryImpl
    ): LoanRequestRepository

    @Binds
    @Singleton
    fun loanConditionsDataSource(
        loanConditionsDataSourceImpl: LoanConditionsDataSourceImpl
    ): LoanConditionsDataSource

    @Binds
    @Singleton
    fun loanConditionsRepository(
        loanConditionsRepositoryImpl: LoanConditionsRepositoryImpl
    ): LoanConditionsRepository

    @Binds
    @Singleton
    fun loansDataSource(loansDataSourceImpl: LoansDataSourceImpl): LoansDataSource

    @Binds
    @Singleton
    fun loansRepository(loansRepositoryImpl: LoansRepositoryImpl): LoansRepository

    @Binds
    @Singleton
    fun networkStateRepository(
        networkStateRepositoryImpl: NetworkStateRepositoryImpl
    ): NetworkStateRepository

    @Binds
    @Singleton
    fun cacheLoansDataSource(
        cacheLoansDataSourceImpl: CacheLoansDataSourceImpl
    ): CacheLoansDataSource
}