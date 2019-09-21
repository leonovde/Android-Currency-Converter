package com.leonov_dev.currencyconverter.preferences;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.preference.PreferenceFragmentCompat;

import com.leonov_dev.currencyconverter.R;

public class CurrencySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_currency);
    }
}
