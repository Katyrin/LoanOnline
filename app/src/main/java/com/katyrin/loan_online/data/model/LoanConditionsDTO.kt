package com.katyrin.loan_online.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanConditionsDTO(
    val maxAmount: Int?,
    val percent: Double?,
    val period: Int?,
) : Parcelable
