package com.leonov_dev.currencyconverter.data.source.remote


import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSource
import com.leonov_dev.currencyconverter.model.RatesGetModel
import kotlinx.coroutines.Deferred

class RemoteDataSource : CurrenciesDataSource.RemoteSource {

    private val ratesRatesClient: RatesClient by lazy { ClientGenerator.createRatesClient() }

    override suspend fun fetchRates(): Deferred<RatesGetModel?> {
        return ratesRatesClient.fetchLatestRinggitRatesAsync()
    }

}
