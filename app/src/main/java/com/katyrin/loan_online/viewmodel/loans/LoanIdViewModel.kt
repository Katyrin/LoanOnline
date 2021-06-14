package com.katyrin.loan_online.viewmodel.loans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import com.katyrin.loan_online.viewmodel.loanrequest.LoanRequestState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanIdViewModel @Inject constructor(
    private val loansRepository: LoansRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _loanRequestState = MutableLiveData<LoanRequestState>()
    val loanRequestState: LiveData<LoanRequestState> = _loanRequestState

    private val _dateText = MutableLiveData<Pair<String, String>>()
    val dateText: LiveData<Pair<String, String>> = _dateText

    fun getLoanInfo(token: String?, id: Int) {
        _loanRequestState.value = LoanRequestState.Loading
        val dispose = token?.let {
            loansRepository
                .getLoanById(it, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
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

    fun getDateText(text: String) {
        val delimiters = arrayOf("T", ".")
        val listDate = text.split(*delimiters)
        val day = listDate[0]
        val time = listDate[1]
        _dateText.value = day to time
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}