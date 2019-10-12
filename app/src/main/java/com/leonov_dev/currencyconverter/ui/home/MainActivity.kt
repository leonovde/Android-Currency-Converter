package com.leonov_dev.currencyconverter.ui.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.ViewModelFactory
import com.leonov_dev.currencyconverter.ui.about.AboutFragment
import com.leonov_dev.currencyconverter.ui.home.converter.ConverterFragment
import com.leonov_dev.currencyconverter.ui.home.stock.StockFragment
import kotlinx.android.synthetic.main.activity_main_wrapper.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {

        private val TAG_TO_FRAGMENT_FACTORY_FUNCTION = mapOf(
                ConverterFragment::class.java.simpleName to { ConverterFragment() },
                StockFragment::class.java.simpleName to { StockFragment() },
                AboutFragment::class.java.simpleName to { AboutFragment() }
        )

        fun obtainViewModel(activity: FragmentActivity): StockConverterViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(StockConverterViewModel::class.java)
        }
    }

    private var viewModel: StockConverterViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_wrapper)

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected)
        viewModel = obtainViewModel(this)
    }

    // Handles bottom navigation bar for android
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_rates -> presentFragment(StockFragment::class.java.simpleName)
            R.id.navigation_converter -> presentFragment(ConverterFragment::class.java.simpleName)
            R.id.navigation_settings -> presentFragment(AboutFragment::class.java.simpleName)
        }
        return true
    }

    // Handles whether to add a new fragment to the fragment container or to show an existing one
    private fun presentFragment(tag: String) {
        require(TAG_TO_FRAGMENT_FACTORY_FUNCTION.containsKey(tag)) { "Unexpected fragment tag" }

        val transaction = supportFragmentManager.beginTransaction()

        // Hide fragments that are not the one being shown.
        TAG_TO_FRAGMENT_FACTORY_FUNCTION.keys
                .filter { it != tag }
                .forEach { it ->
                    supportFragmentManager.findFragmentByTag(it)?.let { transaction.hide(it) }
                }

        // Show or create the desired fragment.
        supportFragmentManager.findFragmentByTag(tag).let {
            if (it == null) {
                val fragment = TAG_TO_FRAGMENT_FACTORY_FUNCTION[tag]!!()
                transaction.add(R.id.fragmentContainer, fragment, tag)
            } else {
                transaction.show(it)
            }
        }
        transaction.commit()
    }
}
