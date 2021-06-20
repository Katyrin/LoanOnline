package com.katyrin.loan_online.data.datasource.session

import android.content.Context
import android.content.SharedPreferences
import com.katyrin.loan_online.R
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(context: Context) : SessionManager {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    override fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(SHARED_PREFERENCES_TOKEN, token)
        editor.apply()
    }

    override fun saveIsRegistered(boolean: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(IS_REGISTERED, boolean)
        editor.apply()
    }

    override fun getIsRegistered(): Boolean = prefs.getBoolean(IS_REGISTERED, false)
    override fun fetchAuthToken(): String? = prefs.getString(SHARED_PREFERENCES_TOKEN, null)

    private companion object {
        const val IS_REGISTERED = "IS_REGISTERED"
        const val SHARED_PREFERENCES_TOKEN = "SHARED_PREFERENCES_TOKEN"
    }
}