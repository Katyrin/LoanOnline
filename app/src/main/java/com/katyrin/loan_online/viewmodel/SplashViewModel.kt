package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.repository.splash.SplashRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository
) : ViewModel() {

    private val _tokenState = MutableLiveData<Boolean>()
    val tokenState: LiveData<Boolean> = _tokenState

    fun fetchAuthToken() {
        _tokenState.value = !splashRepository.fetchAuthToken().isNullOrEmpty()
    }
}