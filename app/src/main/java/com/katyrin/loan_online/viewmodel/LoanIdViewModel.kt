package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.repository.loans.LoansRepository
import com.katyrin.loan_online.utils.DOT
import com.katyrin.loan_online.utils.LETTER_T
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import com.katyrin.loan_online.viewmodel.appstates.setErrorState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanIdViewModel @Inject constructor(
    private val loansRepository: LoansRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _requestState = MutableLiveData<RequestState<LoanDTO>>()
    val requestState: LiveData<RequestState<LoanDTO>> = _requestState

    private val _dateText = MutableLiveData<Pair<String, String>>()
    val dateText: LiveData<Pair<String, String>> = _dateText

    fun getLoanInfo(id: Int) {
        _requestState.value = RequestState.Loading
        disposable?.add(
            loansRepository
                .getLoanById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::successState, ::setErrorStateServer)
        )
    }

    private fun successState(loan: LoanDTO) {
        _requestState.value = RequestState.Success(loan)
    }

    private fun setErrorStateServer(throwable: Throwable) {
        _requestState.setErrorState(throwable)
    }

    fun getDateText(text: String) {
        val delimiters = arrayOf(LETTER_T, DOT)
        val listDate = text.split(*delimiters)
        val day = listDate[0]
        val time = listDate[1]
        _dateText.value = day to time
    }

    override fun onCleared() {
        disposable?.clear()
        disposable = null
        super.onCleared()
    }
}