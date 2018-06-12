package com.leonov_dev.currencyconverter.data.source.local;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.leonov_dev.currencyconverter.data.CurrenciesJson;
import com.leonov_dev.currencyconverter.utils.DateConverter;

@Database(entities = {CurrenciesJson.class}, version = 1, exportSchema = false)
public class CurrencyDatabase extends RoomDatabase{

    private static final String DB_NAME = "CurrenciesJson.db";

    private static CurrencyDatabase instance;

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

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

}
