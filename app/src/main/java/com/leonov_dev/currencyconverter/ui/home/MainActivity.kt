package com.leonov_dev.currencyconverter.ui.home

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.FragmentActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.leonov_dev.currencyconverter.ui.about.AboutFragment
import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.ViewModelFactory
import com.leonov_dev.currencyconverter.preferences.CurrencySettingsActivity
import kotlinx.android.synthetic.main.activity_main_wrapper.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val LOG_TAG = this.javaClass.simpleName
    private var viewModel: StockConverterViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_wrapper)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        val stockAndConverterFragment = StockAndConverterFragment()

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, stockAndConverterFragment)
        ft.addToBackStack(null)
        ft.commit()

        viewModel = obtainViewModel(this)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 1) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val elementId = item.itemId
        if (elementId == R.id.action_settings) {
            val intent = Intent(this, CurrencySettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragmentClass: Class<*>? = null
        var fragment: Fragment? = null

        val id = item.itemId
        if (id == R.id.home_page_item) {
            fragmentClass = StockAndConverterFragment::class.java
        } else if (id == R.id.about_item) {
            fragmentClass = AboutFragment::class.java
        }

        try {
            fragment = fragmentClass!!.newInstance() as Fragment
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error Creating fragment $e")
        }

        val transaction = supportFragmentManager.beginTransaction()
        try {
            transaction.replace(R.id.container, fragment!!)
            transaction.addToBackStack(null)
            transaction.commit()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Transaction error$e")
        }

        title = item.title
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    companion object {

        fun obtainViewModel(activity: FragmentActivity): StockConverterViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(StockConverterViewModel::class.java)
        }
    }
}
