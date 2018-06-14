package com.leonov_dev.currencyconverter.data.source.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApi {

    @GET("MYR")
    Call<PostModel> getCurrencyData(/*@Path("currencyName") String currencyName*/);

}
