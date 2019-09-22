package com.leonov_dev.currencyconverter.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

import com.leonov_dev.currencyconverter.R

class CurrencySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.pref_currency)
    }
}
