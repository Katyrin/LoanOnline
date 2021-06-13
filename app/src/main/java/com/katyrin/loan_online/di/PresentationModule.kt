package com.katyrin.loan_online.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.viewmodel.exit.ExitViewModel
import com.katyrin.loan_online.viewmodel.loanconditions.LoanConditionsViewModel
import com.katyrin.loan_online.viewmodel.loanrequest.LoanRequestViewModel
import com.katyrin.loan_online.viewmodel.loans.LoanIdViewModel
import com.katyrin.loan_online.viewmodel.loans.LoansViewModel
import com.katyrin.loan_online.viewmodel.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanRequestViewModel::class)
    fun bindLoanRequestViewModel(viewModel: LoanRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanConditionsViewModel::class)
    fun loanConditionsViewModel(viewModel: LoanConditionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoansViewModel::class)
    fun loansViewModel(viewModel: LoansViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanIdViewModel::class)
    fun loanIdViewModel(viewModel: LoanIdViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExitViewModel::class)
    fun exitViewModel(viewModel: ExitViewModel): ViewModel
}