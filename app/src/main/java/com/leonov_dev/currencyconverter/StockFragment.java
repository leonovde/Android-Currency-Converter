package com.leonov_dev.currencyconverter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.databinding.ActivityStockBinding;


import java.util.ArrayList;

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

    private void setupListAdapter() {
        ListView listView =  mStockBinding.list;

        mCurrencyAdapter = new CurrencyReplacementAdapter(
                new ArrayList<CurrencyReplacement>(0),
                mStockViewModel
        );
        listView.setAdapter(mCurrencyAdapter);
    }

}