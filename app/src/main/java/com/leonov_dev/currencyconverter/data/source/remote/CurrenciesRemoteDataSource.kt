package com.leonov_dev.currencyconverter.data.source.remote


import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.leonov_dev.currencyconverter.data.source.CurrenciesDataSoruce
import com.leonov_dev.currencyconverter.data.source.local.CurrenciesLocalDataSource
import com.leonov_dev.currencyconverter.utils.AppExecutors

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrenciesRemoteDataSource private constructor(private val mAppExecutors: AppExecutors) : CurrenciesDataSoruce.RemoteSource {

    private val LOG_TAG = CurrenciesRemoteDataSource::class.java.simpleName

    private val BASE_URL = "https://api.exchangeratesapi.io"

    private val retrofit: Retrofit

    private val currencyApi: CurrencyApi

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        currencyApi = retrofit.create(CurrencyApi::class.java)
    }

    override fun downloadCurrenciesJson(callback: CurrenciesDataSoruce.LoadCurrenciesCallback) {
        val runnable = Runnable {
            var response: Response<*>? = null
            try {
                //Sync
                response = currencyApi.currencyData.execute()
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error occurred during network call $e")
                callback.onNothingLoaded()
            }

            var parsedResponse: PostModel? = null
            try {
                parsedResponse = response!!.body() as PostModel?
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error parsing response $e")
            }

            if (parsedResponse != null) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val responseJson = gson.toJson(parsedResponse)
                callback.onCurrencyLoaded(responseJson)
            } else {
                //TODO Make it load from local source
                callback.onNothingLoaded()
            }
        }
        mAppExecutors.networkIO().execute(runnable)

    }

    companion object {

        @Volatile
        private var INSTANCE: CurrenciesRemoteDataSource? = null

        fun getInstance(appExecutors: AppExecutors): CurrenciesRemoteDataSource? {
            if (INSTANCE == null) {
                synchronized(CurrenciesLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = CurrenciesRemoteDataSource(appExecutors)
                    }
                }
            }
            return INSTANCE
        }
    }


}
