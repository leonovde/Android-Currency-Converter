package com.leonov_dev.currencyconverter.data.source;

import android.support.annotation.NonNull;

import com.leonov_dev.currencyconverter.data.CurrenciesJson;
import com.leonov_dev.currencyconverter.data.source.local.CurrencyDao;
import com.leonov_dev.currencyconverter.utils.AppExecutors;

public class CurrenciesRepository implements CurrenciesJsonDataSoruce.LocalSource, CurrenciesJsonDataSoruce.RemoteSource{

    private final CurrencyDao mTaskDao;

    private final AppExecutors mAppExecutors;

    private final String LOG_TAG = CurrenciesRepository.class.getSimpleName();

    public CurrenciesRepository(@NonNull AppExecutors appExecutors, @NonNull CurrencyDao currencyDao){
        mAppExecutors = appExecutors;
        mTaskDao = currencyDao;
    }

    @Override
    public String loadCurrenciesJson() {
        return null;
    }

    @Override
    public void updateCurrenciesJson(CurrenciesJson currenciesJson) {

    }

    @Override
    public void insertCurrenciesJson(CurrenciesJson currenciesJson) {

    }

    @Override
    public String downloadCurrenciesJson() {
        return null;
    }
}
