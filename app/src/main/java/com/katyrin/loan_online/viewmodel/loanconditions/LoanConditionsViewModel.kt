package com.katyrin.loan_online.viewmodel.loanconditions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import com.katyrin.loan_online.data.repository.loanconditions.LoanConditionsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoanConditionsViewModel @Inject constructor(
    private val loanConditionsRepository: LoanConditionsRepository
) : ViewModel() {

    private val _loanConditionsState = MutableLiveData<LoanConditionsState>()
    val loanConditionsState: LiveData<LoanConditionsState> = _loanConditionsState
    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun getLoanConditions(token: String) {
        _loanConditionsState.value = LoanConditionsState.Loading

        disposable?.add(
            loanConditionsRepository.getLoansCondition(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::successState) { setErrorStateServer() }
        )
    }

    private fun successState(loanConditionsDTO: LoanConditionsDTO) {
        _loanConditionsState.value = LoanConditionsState.Success(loanConditionsDTO)
    }

    private fun setErrorStateServer() {
        _loanConditionsState.value = LoanConditionsState.Error
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}