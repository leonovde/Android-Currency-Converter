package com.leonov_dev.currencyconverter.data.source

import com.leonov_dev.currencyconverter.data.source.local.LocalDataSource
import com.leonov_dev.currencyconverter.data.source.remote.RemoteDataSource
import com.leonov_dev.currencyconverter.model.RatesGetModel
import kotlinx.coroutines.Deferred

class CurrenciesRepository(private val localDataSource: LocalDataSource,
                           private val remoteDataSource: RemoteDataSource) :
        CurrenciesDataSource.LocalSource,
        CurrenciesDataSource.RemoteSource {


    override suspend fun fetchRates(): Deferred<RatesGetModel?> {
        return remoteDataSource.fetchRates()
    }
}
