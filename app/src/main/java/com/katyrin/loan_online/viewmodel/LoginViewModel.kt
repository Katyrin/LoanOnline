package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.repository.login.LoginRepository
import com.katyrin.loan_online.utils.QUARTER_SECOND
import com.katyrin.loan_online.viewmodel.appstates.LoginFormState
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import com.katyrin.loan_online.viewmodel.appstates.setErrorState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _saveDataState = MutableLiveData<Boolean>()
    val saveDataState: LiveData<Boolean> = _saveDataState

    private val _registeredState = MutableLiveData<Boolean>()
    val registeredState: LiveData<Boolean> = _registeredState

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _requestState = MutableLiveData<RequestState<String>>()
    val requestState: LiveData<RequestState<String>> = _requestState

    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun registration(username: String, password: String) {
        _requestState.value = RequestState.Loading
        disposable?.add(
            loginRepository.postRegistration(User(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { login(username, password) },
                    { setErrorServerState(it) }
                )
        )
    }

    fun login(username: String, password: String) {
        _requestState.value = RequestState.Loading
        disposable?.add(
            loginRepository.postLogin(User(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::setSuccessLogin, ::setErrorServerState)
        )
    }

    private fun setSuccessLogin(responseBody: ResponseBody) {
        _requestState.value = RequestState.Success(responseBody.string())
    }

    private fun setErrorServerState(throwable: Throwable) {
        _requestState.setErrorState(throwable)
    }

    fun subscribeLoginDataChanged(textInput: Flowable<Pair<String, String>>) {
        disposable?.add(
            textInput
                .debounce(QUARTER_SECOND, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { pair -> loginDataChanged(pair.first, pair.second) }
        )
    }

    private fun loginDataChanged(username: String, password: String) {
        when {
            !isFieldValid(username) -> _loginForm.value = LoginFormState.ErrorUserName
            !isFieldValid(password) -> _loginForm.value = LoginFormState.ErrorPassword
            else -> _loginForm.value = LoginFormState.Success
        }
    }

    private fun isFieldValid(field: String): Boolean {
        return field.length > FIVE_LETTERS
    }

    fun saveIsRegistered(boolean: Boolean) {
        loginRepository.saveIsRegistered(boolean)
    }

    fun getIsRegistered() {
        _registeredState.value = loginRepository.getIsRegistered()
    }

    fun saveData(token: String) {
        loginRepository.saveAuthToken(token)
        _saveDataState.value = loginRepository.getIsRegistered()
    }

    override fun onCleared() {
        disposable?.clear()
        disposable = null
        super.onCleared()
    }

    private companion object {
        const val FIVE_LETTERS = 5
    }
}