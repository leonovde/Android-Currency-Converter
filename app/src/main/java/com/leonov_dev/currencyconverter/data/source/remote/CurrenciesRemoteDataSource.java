package com.leonov_dev.currencyconverter.data.source.remote;

import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;

public class CurrenciesRemoteDataSource implements CurrenciesJsonDataSoruce.RemoteSource {

    private final String URL_MYR = "http://api.fixer.io/latest?base=MYR";

    @Override
    public String downloadCurrenciesJson() {
        return null;
    }
}
