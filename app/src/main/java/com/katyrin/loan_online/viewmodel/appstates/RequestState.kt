package com.katyrin.loan_online.viewmodel.appstates

sealed class RequestState<out T> {
    data class Success<out T>(val value: T) : RequestState<T>()
    data class ClientError(val code: Int) : RequestState<Nothing>()
    object ServerError : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
}