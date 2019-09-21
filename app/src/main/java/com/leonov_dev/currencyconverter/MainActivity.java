package com.leonov_dev.currencyconverter;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.leonov_dev.currencyconverter.preferences.CurrencySettingsActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //TODO get rid of this cludge
    public static boolean isFirstRun = true;

    private final String LOG_TAG = this.getClass().getSimpleName();

    private FragmentTransaction ft;

    private StockConverterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wrapper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment stockAndConverterFragment = new StockAndConverterFragment();

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, stockAndConverterFragment);
        ft.addToBackStack(null);
        ft.commit();

        viewModel = obtainViewModel(this);

    }

    public static StockConverterViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        StockConverterViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(StockConverterViewModel.class);

        return viewModel;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int elementId = item.getItemId();
        if (elementId == R.id.action_settings){
            Intent intent = new Intent(this, CurrencySettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Class fragmentClass = null;
        Fragment fragment = null;

        int id = item.getItemId();
        if (id == R.id.home_page_item){
            fragmentClass = StockAndConverterFragment.class;
        } else if (id == R.id.about_item) {
            fragmentClass = AboutFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Creating fragment " + e);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            Log.e(LOG_TAG, "Transaction error" + e);
        }

        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
