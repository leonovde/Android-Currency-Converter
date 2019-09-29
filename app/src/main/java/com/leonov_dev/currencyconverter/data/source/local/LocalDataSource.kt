package com.leonov_dev.currencyconverter.data.source.local

import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSource
import com.leonov_dev.currencyconverter.utils.AppExecutors

class LocalDataSource
private constructor(private val mAppExecutors: AppExecutors,
                    private val mCurrencyDao: CurrencyDao) : CurrenciesDataSource.LocalSource {

}
