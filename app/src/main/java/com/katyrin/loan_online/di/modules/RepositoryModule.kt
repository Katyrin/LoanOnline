package com.katyrin.loan_online.di.modules

import com.katyrin.loan_online.data.repository.exit.ExitRepository
import com.katyrin.loan_online.data.repository.exit.ExitRepositoryImpl
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
interface RepositoryModule {

    @Binds
    @Singleton
    fun loginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun loanRequestRepository(
        loanRequestRepositoryImpl: LoanRequestRepositoryImpl
    ): LoanRequestRepository

    @Binds
    @Singleton
    fun loanConditionsRepository(
        loanConditionsRepositoryImpl: LoanConditionsRepositoryImpl
    ): LoanConditionsRepository

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
    fun exitRepository(exitRepositoryImpl: ExitRepositoryImpl): ExitRepository
}