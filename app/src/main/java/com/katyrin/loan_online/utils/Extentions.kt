package com.katyrin.loan_online.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.katyrin.loan_online.R
import com.katyrin.loan_online.ui.main.BottomNavigationPosition
import com.katyrin.loan_online.ui.main.createFragment
import com.katyrin.loan_online.ui.main.getTag

private const val ROTATION_ANIMATED_AMOUNT = 1000f
private const val ROTATION_DURATION = 5000L
private const val ERROR_400 = 400
private const val ERROR_401 = 401
private const val ERROR_403 = 403
private const val ERROR_404 = 404

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

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}