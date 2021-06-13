package com.katyrin.loan_online.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.repository.login.LoginRepository
import com.katyrin.loan_online.utils.HALF_SECOND
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

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private var disposable: CompositeDisposable? = CompositeDisposable()
    private var user: User? = null
    private var token: String? = null

    fun registration(username: String, password: String) {
        user = User(username, password)
        val dispose = user?.let {
            loginRepository.postRegistration(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { login(User(username, password)) },
                    { setErrorStateServer() }
                )
        }
        if (dispose != null) {
            disposable?.add(dispose)
        }
    }

    private fun login(user: User) {
        disposable?.add(
            loginRepository.postLogin(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::setSuccessStateServer) { setErrorStateServer() }
        )
    }

    private fun setSuccessStateServer(responseBody: ResponseBody) {
        token = responseBody.string()
        _loginResult.value = LoginResult.Success(token, user)
    }

    private fun setErrorStateServer() {
        _loginResult.value = LoginResult.Error
    }

    fun subscribeLoginDataChanged(textInput: Flowable<Pair<String, String>>) {
        disposable?.add(
            textInput
                .debounce(HALF_SECOND, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { pair -> loginDataChanged(pair.first, pair.second) }
        )
    }

    private fun loginDataChanged(username: String, password: String) {
        if (!isFieldValid(username)) {
            _loginForm.value = LoginFormState.ErrorUserName
        } else if (!isFieldValid(password)) {
            _loginForm.value = LoginFormState.ErrorPassword
        } else {
            _loginForm.value = LoginFormState.Success
        }
    }

    private fun isFieldValid(field: String): Boolean {
        return field.length > 5
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}