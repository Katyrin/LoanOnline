package com.katyrin.loan_online.viewmodel.loans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val loansRepository: LoansRepository
) : ViewModel() {

    private val _loansRequestState = MutableLiveData<LoansRequestState>()
    val loansRequestState: LiveData<LoansRequestState> = _loansRequestState
    private var disposable: CompositeDisposable? = CompositeDisposable()

    fun getLoans() {
        _loansRequestState.value = LoansRequestState.Loading
        disposable?.add(
            loansRepository
                .getLoans()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::successState) { setErrorStateServer() }
        )
    }

    private fun successState(loans: List<LoanDTO>) {
        _loansRequestState.value = LoansRequestState.Success(loans)
    }

    private fun setErrorStateServer() {
        _loansRequestState.value = LoansRequestState.Error
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}