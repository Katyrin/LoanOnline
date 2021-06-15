package com.katyrin.loan_online.viewmodel.appstates

sealed class ImportantDataState {
    object ErrorFirstName : ImportantDataState()
    object ErrorLastName : ImportantDataState()
    object ErrorPhoneNumber : ImportantDataState()
    object Success : ImportantDataState()
}
