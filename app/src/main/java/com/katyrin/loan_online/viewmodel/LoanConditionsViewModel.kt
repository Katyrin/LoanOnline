package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import com.katyrin.loan_online.data.repository.loanconditions.LoanConditionsRepository
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import com.katyrin.loan_online.viewmodel.appstates.setErrorState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoanConditionsViewModel @Inject constructor(
    private val loanConditionsRepository: LoanConditionsRepository
) : ViewModel() {

    private val _requestState = MutableLiveData<RequestState<LoanConditionsDTO>>()
    val requestState: LiveData<RequestState<LoanConditionsDTO>> = _requestState
    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun getLoanConditions() {
        _requestState.value = RequestState.Loading

        disposable?.add(
            loanConditionsRepository.getLoansCondition()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::successState, ::setErrorStateServer)
        )
    }

    private fun successState(loanConditionsDTO: LoanConditionsDTO) {
        _requestState.value = RequestState.Success(loanConditionsDTO)
    }

    private fun setErrorStateServer(throwable: Throwable) {
        _requestState.setErrorState(throwable)
    }

    override fun onCleared() {
        disposable?.clear()
        disposable = null
        super.onCleared()
    }
}