package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    private val _tokenState = MutableLiveData<Boolean>()
    val tokenState: LiveData<Boolean> = _tokenState

    fun checkToken(token: String?) {
        _tokenState.value = !token.isNullOrEmpty()
    }
}