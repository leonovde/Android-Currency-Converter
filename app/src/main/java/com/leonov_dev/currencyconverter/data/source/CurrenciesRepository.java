package com.leonov_dev.currencyconverter.data.source;

import androidx.annotation.NonNull;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.local.CurrenciesLocalDataSource;
import com.leonov_dev.currencyconverter.data.source.remote.CurrenciesRemoteDataSource;

import java.util.List;

public class CurrenciesRepository implements CurrenciesJsonDataSoruce.LocalSource, CurrenciesJsonDataSoruce.RemoteSource{

    private final CurrenciesLocalDataSource mLocalDataSource;

    private final CurrenciesRemoteDataSource mRemoteDataSource;

    private static CurrenciesRepository INSTANCE = null;

    private final String LOG_TAG = CurrenciesRepository.class.getSimpleName();

    public static CurrenciesRepository getInstance(CurrenciesLocalDataSource local,
                                                    CurrenciesRemoteDataSource remote) {
        if (INSTANCE == null) {
            INSTANCE = new CurrenciesRepository(local, remote);
        }
        return INSTANCE;
    }

    private CurrenciesRepository(@NonNull CurrenciesLocalDataSource local,
                                @NonNull CurrenciesRemoteDataSource remote){
        mLocalDataSource = local;
        mRemoteDataSource = remote;
    }


    @Override
    public void loadCurrencyReplacements(final CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback callback) {
        mLocalDataSource.loadCurrencyReplacements(new CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback() {
            @Override
            public void onCurrencyLoaded(List<CurrencyReplacement> currencies) {
                callback.onCurrencyLoaded(currencies);
            }

            @Override
            public void onNothingLoaded() {

            }
        });
    }

    @Override
    public void updateCurrency(CurrencyReplacement currency) {

    }

    @Override
    public void insertCurrency(CurrencyReplacement currency) {

    }

    @Override
    public void insertCurrencies(List<CurrencyReplacement> currencies) {
        if (currencies.isEmpty()){
            return;
        }
        mLocalDataSource.insertCurrencies(currencies);
    }


    @Override
    public void downloadCurrenciesJson(final CurrenciesJsonDataSoruce.LoadCurrenciesCallback callback) {
        mRemoteDataSource.downloadCurrenciesJson(new CurrenciesJsonDataSoruce.LoadCurrenciesCallback() {
            @Override
            public void onCurrencyLoaded(String jsonResponse) {
                callback.onCurrencyLoaded(jsonResponse);
            }

            @Override
            public void onNothingLoaded() {

            }
        });
    }
}
