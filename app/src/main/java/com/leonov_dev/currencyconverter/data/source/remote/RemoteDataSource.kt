package com.leonov_dev.currencyconverter.data.source.remote


import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSource
import com.leonov_dev.currencyconverter.model.Rates
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class RemoteDataSource private constructor() : CurrenciesDataSource.RemoteSource {

    private val ratesRatesClient: RatesClient by lazy { ClientGenerator.createRatesClient() }

    override fun fetchRates(): Deferred<Rates> {
        return CompletableDeferred()
    }

}
