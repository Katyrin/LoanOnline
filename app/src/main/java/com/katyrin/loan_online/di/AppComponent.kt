package com.katyrin.loan_online.di

import com.katyrin.loan_online.ui.loanconditions.LoanConditionsFragment
import com.katyrin.loan_online.ui.loanrequest.LoanRequestFragment
import com.katyrin.loan_online.ui.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        DataModule::class,
        PresentationModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(loanRequestFragment: LoanRequestFragment)
    fun inject(loanConditionsFragment: LoanConditionsFragment)
}