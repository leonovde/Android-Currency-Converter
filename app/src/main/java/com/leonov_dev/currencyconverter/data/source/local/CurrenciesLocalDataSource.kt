package com.leonov_dev.currencyconverter.data.source.local

import com.leonov_dev.currencyconverter.data.CurrencyReplacement
import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSoruce
import com.leonov_dev.currencyconverter.utils.AppExecutors

class CurrenciesLocalDataSource
private constructor(private val mAppExecutors: AppExecutors,
                    private val mCurrencyDao: CurrencyDao) : CurrenciesDataSoruce.LocalSource {


    override fun loadCurrencyReplacements(callback: CurrenciesDataSoruce.LoadLocalCurrenciesCallback) {
        val runnable = Runnable { callback.onCurrencyLoaded(mCurrencyDao.loadCurrencies()) }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun updateCurrency(currency: CurrencyReplacement) {

    }

    override fun insertCurrency(currency: CurrencyReplacement) {
        val runnable = Runnable { mCurrencyDao.insertCurrency(currency) }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun insertCurrencies(currencies: List<CurrencyReplacement>) {
        val runnable = Runnable {
            for (currency in currencies) {
                mCurrencyDao.insertCurrency(currency)
            }
        }
        mAppExecutors.diskIO().execute(runnable)
    }

    companion object {


        @Volatile
        private var INSTANCE: CurrenciesLocalDataSource? = null

        fun getInstance(appExecutors: AppExecutors,
                        tasksDao: CurrencyDao): CurrenciesLocalDataSource? {
            if (INSTANCE == null) {
                synchronized(CurrenciesLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = CurrenciesLocalDataSource(appExecutors, tasksDao)
                    }
                }
            }
            return INSTANCE
        }
    }


}
