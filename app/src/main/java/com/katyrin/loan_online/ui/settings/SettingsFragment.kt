package com.katyrin.loan_online.ui.settings

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.color.MaterialColors
import com.katyrin.loan_online.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickLogOut()
        switchTheme()
    }

    private fun setClickLogOut() {
        val mListPreference: Preference? = findPreference(getString(R.string.log_out_key))
        mListPreference?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                ExitDialog.newInstance(requireActivity().supportFragmentManager)
                true
            }
    }

    private fun switchTheme() {
        val themePref: SwitchPreferenceCompat? = findPreference(getString(R.string.theme_key))
        themePref?.isChecked = isDarkTheme()
        themePref?.setOnPreferenceChangeListener { _, newValue ->
            AppCompatDelegate.setDefaultNightMode(setTheme(newValue as Boolean))
            true
        }
    }

    private fun setTheme(isChecked: Boolean): Int =
        if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

    private fun isDarkTheme(): Boolean =
        MaterialColors.getColor(requireContext(), R.attr.colorOnPrimary, Color.WHITE) == Color.BLACK

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}