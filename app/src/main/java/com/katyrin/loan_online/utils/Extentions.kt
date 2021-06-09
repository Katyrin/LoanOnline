package com.katyrin.loan_online.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.EditText

fun View.setRotateImage() {
    animate()
        .rotationBy(ROTATION_ANIMATED_AMOUNT)
        .setInterpolator(LinearInterpolator())
        .duration = ROTATION_DURATION
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}