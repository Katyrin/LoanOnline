package com.katyrin.loan_online.di

import com.katyrin.loan_online.ui.loanconditions.LoanConditionsFragment
import com.katyrin.loan_online.ui.loanrequest.LoanRequestFragment
import com.katyrin.loan_online.ui.loans.LoanIdFragment
import com.katyrin.loan_online.ui.loans.LoansFragment
import com.katyrin.loan_online.ui.login.LoginFragment
import com.katyrin.loan_online.ui.settings.ExitDialog
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        DataModule::class,
        PresentationModule::class,
        AppModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(loanRequestFragment: LoanRequestFragment)
    fun inject(loanConditionsFragment: LoanConditionsFragment)
    fun inject(loansFragment: LoansFragment)
    fun inject(loanIdFragment: LoanIdFragment)
    fun inject(exitDialog: ExitDialog)
}