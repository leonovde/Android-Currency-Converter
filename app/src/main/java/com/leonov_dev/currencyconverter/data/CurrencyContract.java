package com.leonov_dev.currencyconverter.data;

import android.provider.BaseColumns;

/**
 * Created by dmitrii_leonov on 11.08.17.
 */

public final class CurrencyContract {

    private CurrencyContract(){}

    public static final class CurrencyEntry implements BaseColumns {

        public final static String TABLE_NAME = "currencies";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CURRENCY_JSON = "json";
    }
}
