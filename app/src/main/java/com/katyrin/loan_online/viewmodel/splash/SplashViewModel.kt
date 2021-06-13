package com.katyrin.loan_online.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    private val _tokenState = MutableLiveData<TokenState>()
    val tokenState: LiveData<TokenState> = _tokenState

    fun checkToken(token: String?) {
        if (token.isNullOrEmpty())
            _tokenState.value = TokenState.EMPTY
        else
            _tokenState.value = TokenState.NO_EMPTY
    }
}