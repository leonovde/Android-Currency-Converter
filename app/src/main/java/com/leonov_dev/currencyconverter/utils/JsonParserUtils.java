package com.leonov_dev.currencyconverter.utils;

import android.util.Log;

import com.leonov_dev.currencyconverter.data.CurrencyData;
import com.leonov_dev.currencyconverter.data.CurrencyReplacement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParserUtils {

    public static List<CurrencyReplacement> parseJSON(String jsonString) throws JSONException {
        List<CurrencyReplacement> currencies = new ArrayList<>();
        JSONObject currenciesJSON = new JSONObject(jsonString);
        double priceOnStock;
        for (int i = 0; i < CurrencyData.INSTANCE.getCurAcronyms().length; i++) {
            priceOnStock = currenciesJSON.getDouble(CurrencyData.INSTANCE.getCurAcronyms()[i]);
            currencies.add(new CurrencyReplacement(CurrencyData.INSTANCE.getCurAcronyms()[i], priceOnStock));
            Log.e("TAG", "NAME: " + currencies.get(i).mCurrencyName + "\n" + "PRICE: " + currencies.get(i).mStockPrice);
        }
        return currencies;
    }

}
