package com.leonov_dev.currencyconverter.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.MultiSelectListPreferenceDialogFragmentCompat;

import com.leonov_dev.currencyconverter.R;

/**
 * Created by dmitrii_leonov on 26/02/2018.
 */

public class CurrencySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_currency);
    }
}
