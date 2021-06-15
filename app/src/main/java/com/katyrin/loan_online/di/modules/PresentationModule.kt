package com.katyrin.loan_online.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.di.ViewModelFactory
import com.katyrin.loan_online.di.ViewModelKey
import com.katyrin.loan_online.viewmodel.*
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

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SuccessViewModel::class)
    fun successViewModel(viewModel: SuccessViewModel): ViewModel
}