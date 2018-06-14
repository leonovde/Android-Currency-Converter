package com.leonov_dev.currencyconverter.data.source.local;

import android.support.annotation.NonNull;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;
import com.leonov_dev.currencyconverter.utils.AppExecutors;

import java.util.List;

public class CurrenciesLocalDataSource implements CurrenciesJsonDataSoruce.LocalSource{


    private static volatile CurrenciesLocalDataSource INSTANCE;

    private CurrencyDao mCurrencyDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private CurrenciesLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull CurrencyDao currencyDao) {
        mAppExecutors = appExecutors;
        mCurrencyDao = currencyDao;
    }

    public static CurrenciesLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull CurrencyDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (CurrenciesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CurrenciesLocalDataSource(appExecutors, tasksDao);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void loadCurrencyReplacements(final CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callback.onCurrencyLoaded(mCurrencyDao.loadCurrencies());
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void updateCurrency(CurrencyReplacement currency) {

    }

    @Override
    public void insertCurrency(final CurrencyReplacement currency) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCurrencyDao.insertCurrency(currency);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void insertCurrencies(final List<CurrencyReplacement> currencies) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (CurrencyReplacement currency : currencies) {
                    mCurrencyDao.insertCurrency(currency);
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


}
