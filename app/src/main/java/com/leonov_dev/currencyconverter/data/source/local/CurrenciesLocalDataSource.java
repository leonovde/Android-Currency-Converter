package com.leonov_dev.currencyconverter.data.source.local;

import com.leonov_dev.currencyconverter.data.CurrenciesJson;
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;

public class CurrenciesLocalDataSource implements CurrenciesJsonDataSoruce.LocalSource{

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
}
