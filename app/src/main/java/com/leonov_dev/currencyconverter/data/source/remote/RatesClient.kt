package com.leonov_dev.currencyconverter.data.source.remote

import com.leonov_dev.currencyconverter.model.RatesGetModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface RatesClient {

    @GET("latest")
    fun fetchLatestRinggitRatesAsync(@Path("base") base: String = "MYR"): Deferred<RatesGetModel>
}
