package com.leonov_dev.currencyconverter.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.leonov_dev.currencyconverter.R
import kotlinx.android.synthetic.main.activity_currency_settings.*

class CurrencySettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_settings)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
