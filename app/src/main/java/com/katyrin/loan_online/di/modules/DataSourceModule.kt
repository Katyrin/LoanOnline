package com.katyrin.loan_online.di.modules

import com.katyrin.loan_online.data.datasource.cache.CacheLoansDataSource
import com.katyrin.loan_online.data.datasource.cache.CacheLoansDataSourceImpl
import com.katyrin.loan_online.data.datasource.exit.ExitDataSource
import com.katyrin.loan_online.data.datasource.exit.ExitDataSourceImpl
import com.katyrin.loan_online.data.datasource.loanconditions.LoanConditionsDataSource
import com.katyrin.loan_online.data.datasource.loanconditions.LoanConditionsDataSourceImpl
import com.katyrin.loan_online.data.datasource.loanrequest.LoanRequestDataSource
import com.katyrin.loan_online.data.datasource.loanrequest.LoanRequestDataSourceImpl
import com.katyrin.loan_online.data.datasource.loans.LoansDataSource
import com.katyrin.loan_online.data.datasource.loans.LoansDataSourceImpl
import com.katyrin.loan_online.data.datasource.login.LoginDataSource
import com.katyrin.loan_online.data.datasource.login.LoginDataSourceImpl
import com.katyrin.loan_online.data.datasource.session.SessionManager
import com.katyrin.loan_online.data.datasource.session.SessionManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun loginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource

    @Binds
    @Singleton
    fun loanRequestDataSource(
        loanRequestDataSourceImpl: LoanRequestDataSourceImpl
    ): LoanRequestDataSource

    @Binds
    @Singleton
    fun loanConditionsDataSource(
        loanConditionsDataSourceImpl: LoanConditionsDataSourceImpl
    ): LoanConditionsDataSource

    @Binds
    @Singleton
    fun loansDataSource(loansDataSourceImpl: LoansDataSourceImpl): LoansDataSource

    @Binds
    @Singleton
    fun cacheLoansDataSource(
        cacheLoansDataSourceImpl: CacheLoansDataSourceImpl
    ): CacheLoansDataSource

    @Binds
    @Singleton
    fun exitDataSource(exitDataSourceImpl: ExitDataSourceImpl): ExitDataSource

    @Binds
    @Singleton
    fun sessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager
}