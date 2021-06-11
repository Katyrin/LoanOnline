package com.katyrin.loan_online

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_LOGIN_DATA
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_PASSWORD
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_TOKEN
import com.katyrin.loan_online.utils.SHARED_PREFERENCES_USERNAME

class Prefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_LOGIN_DATA, MODE_PRIVATE)

    var token: String?
        get() = preferences.getString(SHARED_PREFERENCES_TOKEN, "")
        set(value) = preferences.edit().putString(SHARED_PREFERENCES_TOKEN, value).apply()

    var userName: String?
        get() = preferences.getString(SHARED_PREFERENCES_USERNAME, "")
        set(value) = preferences.edit().putString(SHARED_PREFERENCES_USERNAME, value).apply()

    var password: String?
        get() = preferences.getString(SHARED_PREFERENCES_PASSWORD, "")
        set(value) = preferences.edit().putString(SHARED_PREFERENCES_PASSWORD, value).apply()
}