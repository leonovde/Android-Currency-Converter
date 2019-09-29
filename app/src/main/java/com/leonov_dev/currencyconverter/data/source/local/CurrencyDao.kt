package com.leonov_dev.currencyconverter.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.leonov_dev.currencyconverter.data.CurrencyReplacement

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun loadCurrencies(): List<CurrencyReplacement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(currency: CurrencyReplacement)

    @Update
    fun updateCurrency(update: CurrencyReplacement)

}
