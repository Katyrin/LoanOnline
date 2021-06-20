package com.katyrin.loan_online.ui.main

import com.katyrin.loan_online.R
import com.katyrin.loan_online.ui.loanconditions.LoanConditionsFragment
import com.katyrin.loan_online.ui.loans.LoansFragment
import com.katyrin.loan_online.ui.settings.SettingsFragment

enum class BottomNavigationPosition(val id: Int) {
    HOME(R.id.home),
    NEW_LOAN(R.id.new_loan),
    SETTINGS(R.id.settings)
}

fun findNavigationPositionById(id: Int) = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.NEW_LOAN.id -> BottomNavigationPosition.NEW_LOAN
    BottomNavigationPosition.SETTINGS.id -> BottomNavigationPosition.SETTINGS
    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment() = when (this) {
    BottomNavigationPosition.HOME -> LoansFragment.newInstance()
    BottomNavigationPosition.NEW_LOAN -> LoanConditionsFragment.newInstance()
    BottomNavigationPosition.SETTINGS -> SettingsFragment.newInstance()
}

fun BottomNavigationPosition.getTag() = when (this) {
    BottomNavigationPosition.HOME -> LoansFragment.TAG
    BottomNavigationPosition.NEW_LOAN -> LoanConditionsFragment.TAG
    BottomNavigationPosition.SETTINGS -> SettingsFragment.TAG
}


