package com.leonov_dev.currencyconverter.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;

@Database(entities = {CurrencyReplacement.class}, version = 1, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase{

    private static final String DB_NAME = "CurrenciesJson.db";

    private static CurrencyDatabase instance;

    public abstract CurrencyDao taskDao();

    private static final Object sLock = new Object();

    public static CurrencyDatabase getInstance(Context context){
        synchronized (sLock){
            if (instance == null){
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        CurrencyDatabase.class,
                        DB_NAME)
                        .build();
            }
            return instance;
        }
    }

}
