package com.katyrin.loan_online.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.User
import com.katyrin.loan_online.data.repository.login.LoginRepository
import com.katyrin.loan_online.utils.FIVE_LETTERS
import com.katyrin.loan_online.utils.QUARTER_SECOND
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

    fun registration(username: String, password: String) {
        disposable?.add(
            loginRepository.postRegistration(User(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { loginAfterRegistration(User(username, password)) },
                    { setErrorStateServer() }
                )
        )
    }

    private fun loginAfterRegistration(user: User) {
        disposable?.add(
            loginRepository.postLogin(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::setSuccessRegistration) { setErrorStateServer() }
        )
    }

    fun login(username: String, password: String) {
        disposable?.add(
            loginRepository.postLogin(User(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::setSuccessLogin) { setErrorStateServer() }
        )
    }

    private fun setSuccessRegistration(responseBody: ResponseBody) {
        _loginResult.value = LoginResult.SuccessRegistration(responseBody.string())
    }

    private fun setSuccessLogin(responseBody: ResponseBody) {
        _loginResult.value = LoginResult.SuccessLogin(responseBody.string())
    }

    private fun setErrorStateServer() {
        _loginResult.value = LoginResult.Error
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
        if (!isFieldValid(username)) {
            _loginForm.value = LoginFormState.ErrorUserName
        } else if (!isFieldValid(password)) {
            _loginForm.value = LoginFormState.ErrorPassword
        } else {
            _loginForm.value = LoginFormState.Success
        }
    }

    private fun isFieldValid(field: String): Boolean {
        return field.length > FIVE_LETTERS
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}