package com.leonov_dev.currencyconverter.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    List<CurrencyReplacement> loadCurrencies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrency(CurrencyReplacement currency);

    @Update
    void updateCurrency(CurrencyReplacement update);

}
