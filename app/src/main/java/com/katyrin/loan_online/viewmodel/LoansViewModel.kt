package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import com.katyrin.loan_online.viewmodel.appstates.setErrorState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val loansRepository: LoansRepository
) : ViewModel() {

    private val _requestState = MutableLiveData<RequestState<List<LoanDTO>>>()
    val requestState: LiveData<RequestState<List<LoanDTO>>> = _requestState
    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun getLoans() {
        _requestState.value = RequestState.Loading
        disposable?.add(
            loansRepository
                .getLoans()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::successState, ::setErrorStateServer)
        )
    }

    private fun successState(loans: List<LoanDTO>) {
        _requestState.value = RequestState.Success(loans)
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