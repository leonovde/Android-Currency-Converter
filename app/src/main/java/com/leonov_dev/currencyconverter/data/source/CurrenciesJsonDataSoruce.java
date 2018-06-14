package com.leonov_dev.currencyconverter.data.source;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.remote.PostModel;

import java.util.List;

public interface CurrenciesJsonDataSoruce {

    interface LoadCurrenciesCallback {

        void onCurrencyLoaded(String jsonResponse);

        void onNothingLoaded();

    }

    interface  LoadLocalCurrenciesCallback {

        void onCurrencyLoaded(List<CurrencyReplacement> currencies);

        void onNothingLoaded();

    }

    interface LocalSource {

        void loadCurrencyReplacements(LoadLocalCurrenciesCallback callback);

        void updateCurrency(CurrencyReplacement currency);

        void insertCurrency(CurrencyReplacement currency);

        void insertCurrencies(List<CurrencyReplacement> currencies);

    }

    interface RemoteSource {

        void downloadCurrenciesJson(LoadCurrenciesCallback callback);

    }


}
