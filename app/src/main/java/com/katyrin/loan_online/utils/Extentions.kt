package com.katyrin.loan_online.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.katyrin.loan_online.ui.activities.BottomNavigationPosition
import com.katyrin.loan_online.ui.activities.createFragment
import com.katyrin.loan_online.ui.activities.getTag

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