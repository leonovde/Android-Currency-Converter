package com.leonov_dev.currencyconverter.data.source.local

import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSource
import com.leonov_dev.currencyconverter.utils.AppExecutors

class LocalDataSource (private val appExecutors: AppExecutors,
                    private val currencyDao: CurrencyDao) : CurrenciesDataSource.LocalSource {

}
