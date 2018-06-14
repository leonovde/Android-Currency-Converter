package com.leonov_dev.currencyconverter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.leonov_dev.currencyconverter.data.CurrencyData;
import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;
import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository;
import com.leonov_dev.currencyconverter.data.source.remote.PostModel;
import com.leonov_dev.currencyconverter.utils.JsonParserUtils;

import java.util.ArrayList;
import java.util.List;

public class StockConverterViewModel extends AndroidViewModel {

    private final Context mContext; // To avoid leaks, this must be an Application Context.

    private final CurrenciesRepository mCurrenciesRepository;

    private final String LOG_TAG = StockConverterViewModel.class.getSimpleName();

    public final ObservableList<CurrencyReplacement> items = new ObservableArrayList<>();

    public final ObservableBoolean empty = new ObservableBoolean(true);

    public StockConverterViewModel(@NonNull Application application,
                                   @NonNull CurrenciesRepository repository) {
        super(application);
        mContext = application.getApplicationContext();
        mCurrenciesRepository = repository;

    }

    public void loadRemoteData(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo != null && networkInfo.isConnected())) {
            mCurrenciesRepository.downloadCurrenciesJson(new CurrenciesJsonDataSoruce.LoadCurrenciesCallback() {
                @Override
                public void onCurrencyLoaded(String jsonResponse) {
                    saveToDb(jsonResponse);
                }

                @Override
                public void onNothingLoaded() {
                    loadLocalData();
                }

            });
        } else {
            loadLocalData();
        }
    }

    private void saveToDb(String jsonResponse){
        List<CurrencyReplacement> currencies = new ArrayList<>();
        try {
            currencies = JsonParserUtils.parseJSON(jsonResponse);
        } catch (Exception e){
            Log.e(LOG_TAG, "Error Parsing Json " + e);
        }
        for (int i = 0; i < CurrencyData.curAcronyms.length; i++) {
            Log.e("PARSED", "NAME: " + currencies.get(i).mCurrencyName + "\n" + "PRICE: " + currencies.get(i).mStockPrice);
        }
        mCurrenciesRepository.insertCurrencies(currencies);
        loadLocalData();
    }

    private void loadLocalData(){
            mCurrenciesRepository.loadCurrencyReplacements(new CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback() {

                @Override
                public void onCurrencyLoaded(List<CurrencyReplacement> currencies) {
                    List<CurrencyReplacement> filteredCurrencies = new ArrayList<>();
                    Log.e(LOG_TAG, ">>>>>>>>>>>>> UNFILTERED");
                    for (CurrencyReplacement cur : currencies){
                        Log.e(LOG_TAG, " > " + cur.mCurrencyName + " " + cur.mStockPrice);
                    }
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    for (CurrencyReplacement currency : currencies){
                        if (sharedPreferences.getBoolean(
                                currency.getCurrencyName().toLowerCase(),
                                true)) {
                            filteredCurrencies.add(currency);
                        }
                    }
                    Log.e(LOG_TAG, ">>>>>>>>>>>>>>>> FILTERED");
                    for (CurrencyReplacement cur : filteredCurrencies){
                        Log.e(LOG_TAG, " > " + cur.mCurrencyName + "\n > Price: " + cur.mStockPrice);
                    }
                    items.clear();
                    items.addAll(filteredCurrencies);
                    empty.set(items.isEmpty());
                    //Put in list or to binder or what ever laaa
                }

                @Override
                public void onNothingLoaded() {

                }
            });

    }


}
