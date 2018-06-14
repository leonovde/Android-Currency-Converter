package com.leonov_dev.currencyconverter;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov_dev.currencyconverter.data.Currency;
import com.leonov_dev.currencyconverter.data.CurrencyData;
import com.leonov_dev.currencyconverter.data.CurrencyDbHelper;
import com.leonov_dev.currencyconverter.data.CurrencyContract;
import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.databinding.ActivityStockBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment {

    public final String LOG_TAG = this.getClass().getSimpleName();

    private StockConverterViewModel mStockViewModel;

    private ActivityStockBinding mStockBinding;

    private CurrencyReplacementAdapter mCurrencyAdapter;


    public StockFragment() {
        // Required empty public constructor
    }

    //TODO Implement lifecycle?
    @Override
    public void onResume() {
        super.onResume();
        mStockViewModel.loadRemoteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_stock, container, false);

        //Hide notification cation. Notifies that page needs to be refreshed
//        refreshLinearLayout = (LinearLayout) rootView.findViewById(R.id.noDataNotification);
//
//        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().recreate();
//            }
//        });

        mStockBinding = ActivityStockBinding.inflate(inflater, container, false);

        mStockViewModel = MainActivity.obtainViewModel(getActivity());

        mStockBinding.setViewmodel(mStockViewModel);

        setHasOptionsMenu(true);

        return mStockBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private double getPrice(String currencyAcronym, JSONObject rates) {
        double priceOnStock = 0.000;
        try {
            priceOnStock = rates.getDouble(currencyAcronym);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error getting price " + e);
        }
        return priceOnStock;
    }

    private void setupListAdapter() {
        ListView listView =  mStockBinding.list;

        mCurrencyAdapter = new CurrencyReplacementAdapter(
                new ArrayList<CurrencyReplacement>(0),
                mStockViewModel
        );
        listView.setAdapter(mCurrencyAdapter);
    }

}