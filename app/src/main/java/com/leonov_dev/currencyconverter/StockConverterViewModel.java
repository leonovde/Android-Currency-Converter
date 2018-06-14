package com.leonov_dev.currencyconverter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
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

    public StockConverterViewModel(@NonNull Application application,
                                   @NonNull CurrenciesRepository repository) {
        super(application);
        mContext = application.getApplicationContext();
        mCurrenciesRepository = repository;

    }

    public void loadRemoteData(){
        mCurrenciesRepository.downloadCurrenciesJson(new CurrenciesJsonDataSoruce.LoadCurrenciesCallback() {
            @Override
            public void onCurrencyLoaded(String jsonResponse) {
                saveToDb(jsonResponse);
            }

            @Override
            public void onNothingLoaded() {

            }

        });
    }

    private void saveToDb(String jsonResponse){
        List<CurrencyReplacement> currencies = new ArrayList<>();
        try {
            currencies = JsonParserUtils.parseJSON(jsonResponse);
        } catch (Exception e){
            Log.e(LOG_TAG, "Error Parsing Json " + e);
        }
        mCurrenciesRepository.insertCurrencies(currencies);
    }

    public void loadLocalData(){
            mCurrenciesRepository.loadCurrencyReplacements(new CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback() {

                @Override
                public void onCurrencyLoaded(List<CurrencyReplacement> currencies) {
                    List<CurrencyReplacement> filteredCurrencies = new ArrayList<>();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    for (CurrencyReplacement currency : currencies){
                        if (sharedPreferences.getBoolean(
                                currency.getCurrencyName().toLowerCase(),
                                true)) {
                            filteredCurrencies.add(currency);
                        }
                    }
                    items.clear();
                    items.addAll(filteredCurrencies);
                    //Put in list or to binder or what ever laaa
                }

                @Override
                public void onNothingLoaded() {

                }
            });

    }


    /**
     * public LoadFromRemote
     * public Convert
     * private TruncateDecimal
     * private ParseJson (utilsToParse)?
     * private SaveToDb
     *
     */
}
