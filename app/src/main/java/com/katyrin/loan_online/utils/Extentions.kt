package com.katyrin.loan_online.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.katyrin.loan_online.R
import com.katyrin.loan_online.ui.main.BottomNavigationPosition
import com.katyrin.loan_online.ui.main.createFragment
import com.katyrin.loan_online.ui.main.getTag

fun View.setRotateImage() {
    animate()
        .rotationBy(ROTATION_ANIMATED_AMOUNT)
        .setInterpolator(DecelerateInterpolator())
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

fun FragmentManager.findFragment(position: BottomNavigationPosition): Fragment {
    return findFragmentByTag(position.getTag()) ?: position.createFragment()
}

fun Context.showErrorMessage(code: Int) {
    when (code) {
        ERROR_400 -> toast(getString(R.string.exist_user))
        ERROR_401 -> toast(getString(R.string.user_unauthorized))
        ERROR_403 -> toast(getString(R.string.access_denied))
        ERROR_404 -> toast(getString(R.string.user_not_found))
        else -> toast(getString(R.string.internet_error))
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.getDateText(): String = this.split(LETTER_T)[0]