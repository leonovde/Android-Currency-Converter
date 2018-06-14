package com.leonov_dev.currencyconverter.data;

import android.app.Application;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.currencyconverter.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Entity(tableName = "currencies")
public class CurrencyReplacement {

    @PrimaryKey
    @ColumnInfo(name = "currency_id")
    @NonNull
    public String mCurrencyName;
    @ColumnInfo(name = "flag")
    public int mCurrencyFlag;
    @ColumnInfo(name = "quantity")
    public int mQuantity;
    @ColumnInfo(name = "price")
    public double mStockPrice;

    public CurrencyReplacement(){

    }

    //TODO OMG WHAT IS THIS I WAS STRUGGLING HAVING ONE SINGLE CONSTRUCTOR...
    //TODO when you insert using dao, default constructor is used on object
    @Ignore
    public CurrencyReplacement(@NonNull String currencyName, @NonNull double stockPrice){
        mCurrencyName = currencyName;
        mStockPrice = flipPrice(stockPrice);
        setSellBuyQuantityStock(mCurrencyName, mStockPrice);
        Log.e("CREATED", mCurrencyName + " price " + mStockPrice);
    }

    //flip the price from MYR to CURRENCY => CURRENCY to MYR
    private double flipPrice(double price){
        return 1 / price;
    }

    //formats the price to accuracy of 3 decimal points
    public double formatPrice(double price){
        DecimalFormat formatter = new DecimalFormat("0.000");
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return Double.parseDouble(formatter.format(price));
    }

    private void setSellBuyQuantityStock (String currencyName,double price){
        //price varies depends on currency
        switch (currencyName){
            case "USD":
                setQuantityStockFlag(1, price, R.drawable.flag_united_states_of_america);
                return;
            case "EUR":
                setQuantityStockFlag(1, price, R.drawable.flag_eu);
                return;
            case "SGD":
                setQuantityStockFlag(1, price, R.drawable.flag_sg);
                return;
            case "IDR":
                setQuantityStockFlag(1000000, price, R.drawable.flag_indo);
                return;
            case "THB":
                setQuantityStockFlag(100, price, R.drawable.flag_thai);
                return;
            case "PHP":
                setQuantityStockFlag(100, price, R.drawable.flag_phil);
                return;
            case "HKD":
                setQuantityStockFlag(100, price, R.drawable.flag_hk);
                return;
            case "AUD":
                setQuantityStockFlag(1, price, R.drawable.flag_au);
                return;
            case "INR":
                setQuantityStockFlag(100, price, R.drawable.flag_india);
                return;
            case "JPY":
                setQuantityStockFlag(100, price, R.drawable.flag_japan);
                return;
            case "KRW":
                setQuantityStockFlag(10000, price, R.drawable.flag_skor);
                return;
            case "CNY":
                setQuantityStockFlag(100, price, R.drawable.flag_chn);
                return;
            case "GBP":
                setQuantityStockFlag(1, price, R.drawable.flag_uk);
                return;
            default:
                setQuantityStockFlag(0, 0, R.drawable.flag_circle);
        }
    }

    // to make setSellBuyQuantityStock shorter and flexible
    public void setQuantityStockFlag(int quantity, double price, int flagId){
        mQuantity = quantity;
        mStockPrice = formatPrice(mQuantity * price);
        mCurrencyFlag = flagId;
    }


    public int getCurrencyFlag() {
        return mCurrencyFlag;
    }

    public String getCurrencyName(){
        return mCurrencyName;
    }

    public void SetCurrencyName(String currencyName){
        mCurrencyName = currencyName;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public double getStockPrice(){
        return mStockPrice;
    }

}
