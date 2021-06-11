package com.katyrin.loan_online.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanRequest(
    val amount: Int? = 0,
    val firstName: String? = "",
    val lastName: String? = "",
    val percent: Double? = 0.0,
    val period: Int? = 0,
    val phoneNumber: String? = ""
) : Parcelable