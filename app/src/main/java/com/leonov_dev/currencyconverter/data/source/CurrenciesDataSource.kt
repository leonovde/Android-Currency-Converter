package com.leonov_dev.currencyconverter.data.source

import com.leonov_dev.currencyconverter.model.RatesGetModel

import kotlinx.coroutines.Deferred

interface CurrenciesDataSource {

    interface LocalSource

    interface RemoteSource {

        suspend fun fetchRates(): Deferred<RatesGetModel?>

    }
}
