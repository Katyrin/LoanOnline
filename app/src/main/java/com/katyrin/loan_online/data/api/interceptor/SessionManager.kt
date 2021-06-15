package com.katyrin.loan_online.data.api.interceptor

import android.content.Context
import android.content.SharedPreferences
import com.katyrin.loan_online.R
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_PASSWORD
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_TOKEN
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_USERNAME
import javax.inject.Inject

class SessionManager @Inject constructor(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(token: String?, userName: String?, password: String?) {
        val editor = prefs.edit()
        editor.putString(SHARED_PREFERENCES_TOKEN, token)
        editor.putString(SHARED_PREFERENCES_USERNAME, userName)
        editor.putString(SHARED_PREFERENCES_PASSWORD, password)
        editor.apply()
    }

    fun fetchAuthToken(): String? = prefs.getString(SHARED_PREFERENCES_TOKEN, null)
    fun fetchUserName(): String? = prefs.getString(SHARED_PREFERENCES_USERNAME, null)
}