package com.leonov_dev.currencyconverter.data.source;

import com.leonov_dev.currencyconverter.data.CurrenciesJson;

public interface CurrenciesJsonDataSoruce {

    interface LocalSource{

        String loadCurrenciesJson();

        void updateCurrenciesJson(CurrenciesJson currenciesJson);

        void insertCurrenciesJson(CurrenciesJson currenciesJson);

    }

    interface RemoteSource{

        String downloadCurrenciesJson();

    }


}
