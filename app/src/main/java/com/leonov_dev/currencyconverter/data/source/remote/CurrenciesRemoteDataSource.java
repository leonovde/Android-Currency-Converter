package com.leonov_dev.currencyconverter.data.source.remote;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;
import com.leonov_dev.currencyconverter.data.source.local.CurrenciesLocalDataSource;
import com.leonov_dev.currencyconverter.data.source.local.CurrencyDao;
import com.leonov_dev.currencyconverter.utils.AppExecutors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrenciesRemoteDataSource implements CurrenciesJsonDataSoruce.RemoteSource {

    private final String LOG_TAG = CurrenciesRemoteDataSource.class.getSimpleName();

    private final String URL_MYR = "http://api.fixer.io/latest?base=MYR";
    private final String NEW_URL_MYR = "http://api.aoikujira.com/kawase/json/MYR";
    private final String BASE_URL = "http://api.aoikujira.com/kawase/json/";

    private final String BASE_CURRENCY = "MYR";

    private Retrofit retrofit;

    private CurrencyApi currencyApi;

    private AppExecutors mAppExecutors;

    private static volatile CurrenciesRemoteDataSource INSTANCE;

    public static CurrenciesRemoteDataSource getInstance(AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (CurrenciesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CurrenciesRemoteDataSource(appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    private CurrenciesRemoteDataSource(AppExecutors appExecutors){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyApi = retrofit.create(CurrencyApi.class);
        mAppExecutors = appExecutors;
    }

    @Override
    public void downloadCurrenciesJson(final CurrenciesJsonDataSoruce.LoadCurrenciesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //Sync
                    response = currencyApi.getCurrencyData().execute();
                } catch (Exception e){
                    Log.e(LOG_TAG, "Error occurred during network call " + e);
                    callback.onNothingLoaded();
                }

                PostModel parsedResponse = null;
                try {
                    parsedResponse = (PostModel) response.body();
                }catch (Exception e){
                    Log.e(LOG_TAG, "Error parsing response " + e);
                }
                if (parsedResponse != null) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String responseJson = gson.toJson(parsedResponse);
                    callback.onCurrencyLoaded(responseJson);
                } else {
                    //TODO Make it load from local source
                    callback.onNothingLoaded();
                }
            }
        };
        mAppExecutors.networkIO().execute(runnable);

    }


}
