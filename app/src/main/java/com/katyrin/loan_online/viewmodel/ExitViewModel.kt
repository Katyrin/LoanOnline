package com.katyrin.loan_online.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.data.repository.exit.ExitRepository
import com.katyrin.loan_online.viewmodel.appstates.DeleteDBState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExitViewModel @Inject constructor(
    private val exitRepository: ExitRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = CompositeDisposable()
    private val _deleteDBState = MutableLiveData<DeleteDBState>()
    val deleteDBState: LiveData<DeleteDBState> = _deleteDBState

    fun clearData() {
        disposable?.add(
            exitRepository.deleteLoansTable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(::successDeleteData) { errorDeleteData() }
        )
    }

    private fun successDeleteData() {
        _deleteDBState.value = DeleteDBState.SUCCESS
    }

    private fun errorDeleteData() {
        _deleteDBState.value = DeleteDBState.ERROR
    }

    override fun onCleared() {
        disposable?.clear()
        disposable = null
        super.onCleared()
    }
}