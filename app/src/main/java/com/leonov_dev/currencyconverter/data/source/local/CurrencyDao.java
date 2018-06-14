package com.leonov_dev.currencyconverter.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
