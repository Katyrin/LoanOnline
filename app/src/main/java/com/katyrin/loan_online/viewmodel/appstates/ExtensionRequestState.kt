package com.katyrin.loan_online.viewmodel.appstates

import androidx.lifecycle.MutableLiveData
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_CHAR = 0
private const val INTERNET_ERROR = 0
private const val CHAR_FIVE = '5'

fun <T> MutableLiveData<RequestState<T>>.setErrorState(throwable: Throwable) {
    when (throwable) {
        is IOException -> {
            this.value = RequestState.ClientError(INTERNET_ERROR)
        }
        is HttpException -> {
            this.value = getErrorType(throwable.code())
        }
    }
}

private fun <T> getErrorType(code: Int): RequestState<T> =
    if (code.toString()[FIRST_CHAR] == CHAR_FIVE) RequestState.ServerError
    else RequestState.ClientError(code)