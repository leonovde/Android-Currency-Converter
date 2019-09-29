package com.leonov_dev.currencyconverter.data.source

import com.leonov_dev.currencyconverter.data.source.local.LocalDataSource
import com.leonov_dev.currencyconverter.data.source.remote.RemoteDataSource
import com.leonov_dev.currencyconverter.model.Rates
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class CurrenciesRepository private constructor(private val localDataSource: LocalDataSource,
                                               private val remoteDataSource: RemoteDataSource) :
    CurrenciesDataSource.LocalSource,
    CurrenciesDataSource.RemoteSource {


    override fun fetchRates(): Deferred<Rates> {
        return CompletableDeferred()
    }
}
