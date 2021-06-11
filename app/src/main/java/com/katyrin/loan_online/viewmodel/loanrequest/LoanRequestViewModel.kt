package com.katyrin.loan_online.viewmodel.loanrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import com.katyrin.loan_online.data.repository.loanrequest.LoanRequestRepository
import com.katyrin.loan_online.utils.HALF_SECOND
import com.katyrin.loan_online.utils.MINIMUM_PHONE_NUMBER
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoanRequestViewModel @Inject constructor(
    private val loanRequestRepository: LoanRequestRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()

    private val _dataForm = MutableLiveData<ImportantDataState>()
    val dataFormState: LiveData<ImportantDataState> = _dataForm

    private val _loanRequestResult = MutableLiveData<LoanRequestResult>()
    val loanRequestResult: LiveData<LoanRequestResult> = _loanRequestResult

    fun sendRequest(token: String?, loanRequest: LoanRequest) {
        val dispose = token?.let {
            loanRequestRepository
                .postLoansRequest(it, loanRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::successState) { setErrorStateServer() }
        }
        if (dispose != null) {
            disposable?.add(dispose)
        }
    }

    private fun successState(loanDTO: LoanDTO) {
        _loanRequestResult.value = LoanRequestResult.Success(loanDTO)
    }

    private fun setErrorStateServer() {
        _loanRequestResult.value = LoanRequestResult.Error
    }

    fun subscribeImportantDataChanged(textInput: Flowable<Triple<String, String, String>>) {
        disposable?.add(
            textInput
                .debounce(HALF_SECOND, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { triple ->
                    importantDataChanged(triple.first, triple.second, triple.third)
                }
        )
    }

    private fun importantDataChanged(firstName: String, lastName: String, phoneNumber: String) {
        if (isFieldBlank(firstName)) {
            _dataForm.value = ImportantDataState.ErrorFirstName
        } else if (isFieldBlank(lastName)) {
            _dataForm.value = ImportantDataState.ErrorLastName
        } else if (isFieldBlank(phoneNumber) || phoneNumber.length < MINIMUM_PHONE_NUMBER) {
            _dataForm.value = ImportantDataState.ErrorPhoneNumber
        } else {
            _dataForm.value = ImportantDataState.Success
        }
    }

    private fun isFieldBlank(field: String): Boolean {
        return field.isBlank()
    }

    override fun onCleared() {
        if (disposable != null) {
            disposable?.clear()
            disposable = null
        }
        super.onCleared()
    }
}