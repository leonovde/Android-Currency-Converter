package com.leonov_dev.currencyconverter;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce;
import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository;
import com.leonov_dev.currencyconverter.utils.JsonParserUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockConverterViewModel extends AndroidViewModel {

    private final Context mContext; // AppContext

    private final CurrenciesRepository mCurrenciesRepository;

    private final String LOG_TAG = StockConverterViewModel.class.getSimpleName();

    public final ObservableList<CurrencyReplacement> items = new ObservableArrayList<>();

    public final ObservableBoolean empty = new ObservableBoolean(true);

    //Converter activity
    public ObservableField<String> myrAmount = new ObservableField<>();
    public ObservableField<String> otherAmount = new ObservableField<>();
    public ObservableField<CurrencyReplacement> currentCurrency = new ObservableField<>();
    private ObservableArrayList<String> mListCurrencies = new ObservableArrayList<>();
    private HashMap<String, CurrencyReplacement> mCurrencyMap = new HashMap<>();

    private boolean isMyrFocused = false;
    private boolean isOtherFocused = false;
    private final int MYR_FLAG = 10;
    private final int OTHERS_FLAG = 20;

    public StockConverterViewModel(@NonNull Application application,
                                   @NonNull CurrenciesRepository repository) {
        super(application);
        mContext = application.getApplicationContext();
        mCurrenciesRepository = repository;
    }

    public void loadRemoteData(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null && networkInfo.isConnected())) {
            mCurrenciesRepository.downloadCurrenciesJson(new CurrenciesJsonDataSoruce.LoadCurrenciesCallback() {
                @Override
                public void onCurrencyLoaded(String jsonResponse) {
                    saveToDb(jsonResponse);
                }

                @Override
                public void onNothingLoaded() {
                    loadLocalData();
                }

            });
        } else {
            loadLocalData();
        }
    }

    private void saveToDb(String jsonResponse){
        List<CurrencyReplacement> currencies = new ArrayList<>();
        try {
            currencies = JsonParserUtils.parseJSON(jsonResponse);
        } catch (Exception e){
            Log.e(LOG_TAG, "Error Parsing Json " + e);
        }
        mCurrenciesRepository.insertCurrencies(currencies);
        loadLocalData();
    }

    private void loadLocalData(){
            mCurrenciesRepository.loadCurrencyReplacements(new CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback() {
                @Override
                public void onCurrencyLoaded(List<CurrencyReplacement> currencies) {
                    //Store currencies refer from Converter
                    mapCurrencies(currencies);
                    List<CurrencyReplacement> filteredCurrencies = new ArrayList<>();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    for (CurrencyReplacement currency : currencies){
                        if (sharedPreferences.getBoolean(
                                currency.getCurrencyName().toLowerCase(),
                                true)) {
                            filteredCurrencies.add(currency);
                        }
                    }
                    items.clear();
                    items.addAll(filteredCurrencies);
                    empty.set(items.isEmpty());
                    currentCurrency.set(items.get(0));
                }

                @Override
                public void onNothingLoaded() {

                }
            });
    }

    private void mapCurrencies(List<CurrencyReplacement> currencies){
        if (currencies != null) {
            ArrayList<String> bufCurrencies = new ArrayList<>();
            for (int i = 0; i < currencies.size(); i++) {
                bufCurrencies.add(currencies.get(i).mCurrencyName);
                mCurrencyMap.put(currencies.get(i).mCurrencyName, currencies.get(i));
            }
            mListCurrencies.addAll(bufCurrencies);
        }
    }

    private boolean isDouble(String str) {
        try {
            double buf = Double.parseDouble(str);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    //Pass the price from TextView Edited
    private String calculateConversion(String amount, int MyrOrOthers){
        //Currency may not be loaded yet
        try {
            double amountFrom = Double.parseDouble(amount);
            int quantity = currentCurrency.get().mQuantity;
            double price = Double.parseDouble(formatPrice(currentCurrency.get().mStockPrice));
            Log.e(LOG_TAG, "Price : " + price + " Quantity " + quantity);
            if (MyrOrOthers == OTHERS_FLAG) {
                return formatPrice(price / quantity * amountFrom);
            } else {
                return formatPrice(quantity / price * amountFrom);
            }
        } catch (Exception e){
            Log.e(LOG_TAG, "Error converting " + e);
        }
        return "";
    }

    public String formatPrice(double price){
        Log.e(LOG_TAG, ">>>>> Before formatting : " + price);
        DecimalFormat formatter = new DecimalFormat("0.00");
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        return formatter.format(Math.floor(price * 100) / 100);
    }

    public void onMyrTextChanged(CharSequence s, int start, int before, int count){
        if (isMyrFocused) {
            myrAmount.set(s.toString());
            String amount = myrAmount.get();
            if (isDouble(amount)) {
                otherAmount.set(String.valueOf(calculateConversion(amount, MYR_FLAG)));
            } else {
                otherAmount.set("");
            }
        }
    }

    public void onOtherTextChanged(CharSequence s, int start, int before, int count){
        if (isOtherFocused) {
            otherAmount.set(s.toString());
            String amount = otherAmount.get();
            if (isDouble(amount)) {
                myrAmount.set(String.valueOf(calculateConversion(amount, OTHERS_FLAG)));
            } else {
                myrAmount.set("");
            }
        }
    }

    @BindingAdapter("app:onFocusChange")
    public static void onFocusChange(EditText text, final View.OnFocusChangeListener listener) {
        text.setOnFocusChangeListener(listener);
    }

    public View.OnFocusChangeListener getOnFocusChangeListener() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (view.getId() == R.id.convert_MYR_amount){
                    isMyrFocused = true;
                    isOtherFocused = false;
                } else if (view.getId() == R.id.convert_others_currency_amount) {
                    isOtherFocused = true;
                    isMyrFocused = false;
                }
            }
        };
    }

    public void onCurrencyItemSelected(AdapterView<?> parent, View view, int position, long id) {
         String currencyAbriviation = parent.getItemAtPosition(position).toString();
         if (mCurrencyMap.containsKey(currencyAbriviation)){
             currentCurrency.set(mCurrencyMap.get(currencyAbriviation));
         }

    }

}
