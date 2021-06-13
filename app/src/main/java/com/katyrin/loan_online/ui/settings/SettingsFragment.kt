package com.katyrin.loan_online.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.katyrin.loan_online.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickLogOut()
        setSwitchTheme()
    }

    private fun setClickLogOut() {
        val mListPreference: Preference? =
            preferenceManager.findPreference(getString(R.string.log_out_key))

        mListPreference?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                ExitDialog.newInstance(requireActivity().supportFragmentManager)
                return@OnPreferenceClickListener true
            }
    }

    private fun setSwitchTheme() {

    }

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}