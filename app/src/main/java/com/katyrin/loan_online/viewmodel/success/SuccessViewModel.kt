package com.katyrin.loan_online.viewmodel.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.loan_online.utils.DOT
import com.katyrin.loan_online.utils.LETTER_T
import javax.inject.Inject

class SuccessViewModel @Inject constructor() : ViewModel() {

    private val _dateText = MutableLiveData<Pair<String, String>>()
    val dateText: LiveData<Pair<String, String>> = _dateText

    fun getDateText(text: String) {
        val delimiters = arrayOf(LETTER_T, DOT)
        val listDate = text.split(*delimiters)
        val day = listDate[0]
        val time = listDate[1]
        _dateText.value = day to time
    }
}