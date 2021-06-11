package com.katyrin.loan_online.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.katyrin.loan_online.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}