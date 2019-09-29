package com.leonov_dev.currencyconverter.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientGenerator {

    const val RATES_BASE_URL = "https://api.exchangeratesapi.io"

    private val okHttpClient = OkHttpClient.Builder().build()

    private val ratesRetrofitBuilder = Retrofit.Builder()
        .baseUrl(RATES_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

    fun createRatesClient() = ratesRetrofitBuilder.build().create(RatesClient::class.java)
}