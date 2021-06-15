package com.katyrin.loan_online.viewmodel.appstates

import androidx.lifecycle.MutableLiveData
import com.katyrin.loan_online.utils.CHAR_FIVE
import com.katyrin.loan_online.utils.FIRST_CHAR
import com.katyrin.loan_online.utils.INTERNET_ERROR
import retrofit2.HttpException
import java.io.IOException

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