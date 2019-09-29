package com.leonov_dev.currencyconverter.data

import android.provider.BaseColumns

object CurrencyContract {

    class CurrencyEntry : BaseColumns {
        companion object {

            const val TABLE_NAME = "currencies"
            const val _ID = BaseColumns._ID
            const val COLUMN_CURRENCY_JSON = "json"
        }
    }
}
