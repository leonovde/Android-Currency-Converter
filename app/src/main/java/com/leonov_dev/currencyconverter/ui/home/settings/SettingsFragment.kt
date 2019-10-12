package com.leonov_dev.currencyconverter.ui.home.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View

import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.preferences.CurrencySettingsActivity
import kotlinx.android.synthetic.main.fragment_settings_fragment.*

class SettingsFragment : Fragment(R.layout.fragment_settings_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currenciesLayout.setOnClickListener { onCurrenciesSettingsClicked() }
        aboutLayout.setOnClickListener { onAboutClicked() }
    }

    private fun onCurrenciesSettingsClicked() {
        val intent = Intent(context, CurrencySettingsActivity::class.java)
        startActivity(intent)
    }

    private fun onAboutClicked() {
        val intent = Intent(context, AboutFragment::class.java)
        startActivity(intent)
    }
}
