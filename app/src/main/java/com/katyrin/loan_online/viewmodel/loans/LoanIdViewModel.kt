package com.katyrin.loan_online.viewmodel.loans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import com.katyrin.loan_online.viewmodel.loanrequest.LoanRequestState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoanIdViewModel @Inject constructor(
    private val loansRepository: LoansRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _loanRequestState = MutableLiveData<LoanRequestState>()
    val loanRequestState: LiveData<LoanRequestState> = _loanRequestState

    fun getLoanInfo(token: String?, id: Int) {
        _loanRequestState.value = LoanRequestState.Loading
        val dispose = token?.let {
            loansRepository
                .getLoanFromId(it, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::successState) { setErrorStateServer() }
        }
        if (dispose != null) {
            disposable?.add(dispose)
        }
    }

    private fun successState(loan: LoanDTO) {
        _loanRequestState.value = LoanRequestState.Success(loan)
    }

    private fun setErrorStateServer() {
        _loanRequestState.value = LoanRequestState.Error
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}