package com.leonov_dev.currencyconverter.data.source.remote

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception

object ClientGenerator {

    const val RATES_BASE_URL = "https://api.exchangeratesapi.io"

    private val okHttpClient = OkHttpClient
            .Builder()
            .cache(provideCache())
            .build()

    private val ratesRetrofitBuilder = Retrofit.Builder()
            .baseUrl(RATES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    fun createRatesClient(): RatesClient = ratesRetrofitBuilder.build().create(RatesClient::class.java)

    private fun provideCache(): Cache? = try {
        val cacheSize = 1024L * 1024L // 1 MB
        val cacheFile = File(Application().cacheDir, "http-cache")
        Cache(cacheFile, cacheSize)
    } catch (exception: Exception) {
        null
    }
}
