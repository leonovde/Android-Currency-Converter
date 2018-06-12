package com.leonov_dev.currencyconverter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.leonov_dev.currencyconverter.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "currencies_json")
public class CurrenciesJson {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "currency_id")
    @NonNull
    public int mId;
    @ColumnInfo(name = "json_content")
    private String mJsonContent;
    @ColumnInfo(name = "created_date")
    public long mDate;

    public CurrenciesJson(String jsonContent, long date){
        mJsonContent = jsonContent;
        mDate = date;
    }

    @NonNull
    public int getCurrencyId() {
        return mId;
    }

    public String getJsonContent() {
        return mJsonContent;
    }

    public long getCreatedDate() {
        return mDate;
    }

    public void setCurrencyId(@NonNull int mId) {
        this.mId = mId;
    }

    public void setJsonContent(String mJsonContent) {
        this.mJsonContent = mJsonContent;
    }

    public void setCreatedDate(long date) {
        this.mDate = date;
    }
}
