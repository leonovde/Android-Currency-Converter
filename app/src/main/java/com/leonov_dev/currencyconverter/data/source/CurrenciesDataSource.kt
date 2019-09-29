package com.leonov_dev.currencyconverter.data.source

import com.leonov_dev.currencyconverter.model.Rates

import kotlinx.coroutines.Deferred

interface CurrenciesDataSource {

    interface LocalSource

    interface RemoteSource {

        fun fetchRates(): Deferred<Rates>

    }


}
