package com.leonov_dev.currencyconverter.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.content.Context

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import android.net.ConnectivityManager
import android.preference.PreferenceManager

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText

import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.data.CurrencyReplacement
import com.leonov_dev.currencyconverter.data.source.CurrenciesJsonDataSoruce
import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository
import com.leonov_dev.currencyconverter.utils.JsonParserUtils

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap

class StockConverterViewModel(application: Application,
                              private val mCurrenciesRepository: CurrenciesRepository) : AndroidViewModel(application) {

    private val mContext: Context // AppContext

    private val LOG_TAG = StockConverterViewModel::class.java.simpleName

    val items: ObservableList<CurrencyReplacement> = ObservableArrayList()

    val empty = ObservableBoolean(true)

    //Converter activity
    var myrAmount = ObservableField<String>()
    var otherAmount = ObservableField<String>()
    var currentCurrency = ObservableField<CurrencyReplacement>()
    private val mListCurrencies = ObservableArrayList<String>()
    private val mCurrencyMap = HashMap<String, CurrencyReplacement>()

    private var isMyrFocused = false
    private var isOtherFocused = false
    private val MYR_FLAG = 10
    private val OTHERS_FLAG = 20

    val onFocusChangeListener: View.OnFocusChangeListener
        get() = View.OnFocusChangeListener { view, isFocused ->
            if (view.id == R.id.convert_MYR_amount) {
                isMyrFocused = true
                isOtherFocused = false
            } else if (view.id == R.id.convert_others_currency_amount) {
                isOtherFocused = true
                isMyrFocused = false
            }
        }

    init {
        mContext = application.applicationContext
    }

    fun loadRemoteData() {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            mCurrenciesRepository.downloadCurrenciesJson(object : CurrenciesJsonDataSoruce.LoadCurrenciesCallback {
                override fun onCurrencyLoaded(jsonResponse: String) {
                    saveToDb(jsonResponse)
                }

                override fun onNothingLoaded() {
                    loadLocalData()
                }

            })
        } else {
            loadLocalData()
        }
    }

    private fun saveToDb(jsonResponse: String) {
        var currencies: List<CurrencyReplacement> = ArrayList()
        try {
            currencies = JsonParserUtils.parseJSON(jsonResponse)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error Parsing Json $e")
        }

        mCurrenciesRepository.insertCurrencies(currencies)
        loadLocalData()
    }

    private fun loadLocalData() {
        mCurrenciesRepository.loadCurrencyReplacements(object : CurrenciesJsonDataSoruce.LoadLocalCurrenciesCallback {
            override fun onCurrencyLoaded(currencies: List<CurrencyReplacement>) {
                //Store currencies refer from Converter
                mapCurrencies(currencies)
                val filteredCurrencies = ArrayList<CurrencyReplacement>()
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
                for (currency in currencies) {
                    if (sharedPreferences.getBoolean(
                            currency.currencyName.toLowerCase(),
                            true)) {
                        filteredCurrencies.add(currency)
                    }
                }
                items.clear()
                items.addAll(filteredCurrencies)
                empty.set(items.isEmpty())
                currentCurrency.set(items[0])
            }

            override fun onNothingLoaded() {

            }
        })
    }

    private fun mapCurrencies(currencies: List<CurrencyReplacement>?) {
        if (currencies != null) {
            val bufCurrencies = ArrayList<String>()
            for (i in currencies.indices) {
                bufCurrencies.add(currencies[i].mCurrencyName)
                mCurrencyMap[currencies[i].mCurrencyName] = currencies[i]
            }
            mListCurrencies.addAll(bufCurrencies)
        }
    }

    private fun isDouble(str: String?): Boolean {
        try {
            val buf = java.lang.Double.parseDouble(str!!)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    //Pass the price from TextView Edited
    private fun calculateConversion(amount: String?, MyrOrOthers: Int): String {
        //Currency may not be loaded yet
        return try {
            val amountFrom = java.lang.Double.parseDouble(amount!!)
            val quantity = currentCurrency.get()!!.mQuantity
            val price = java.lang.Double.parseDouble(formatPrice(currentCurrency.get()!!.mStockPrice))
            return if (MyrOrOthers == OTHERS_FLAG) {
                formatPrice(price / quantity * amountFrom)
            } else {
                formatPrice(quantity / price * amountFrom)
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error converting $e")
            ""
        }
    }

    private fun formatPrice(price: Double): String {
        val formatter = DecimalFormat("0.00")
        val symbols = formatter.decimalFormatSymbols
        symbols.decimalSeparator = '.'
        return formatter.format(Math.floor(price * 100) / 100)
    }

    fun onMyrTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isMyrFocused) {
            myrAmount.set(s.toString())
            val amount = myrAmount.get()
            if (isDouble(amount)) {
                otherAmount.set(calculateConversion(amount, MYR_FLAG))
            } else {
                otherAmount.set("")
            }
        }
    }

    fun onOtherTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isOtherFocused) {
            otherAmount.set(s.toString())
            val amount = otherAmount.get()
            if (isDouble(amount)) {
                myrAmount.set(calculateConversion(amount, OTHERS_FLAG))
            } else {
                myrAmount.set("")
            }
        }
    }

    fun onCurrencyItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val currencyAbriviation = parent.getItemAtPosition(position).toString()
        if (mCurrencyMap.containsKey(currencyAbriviation)) {
            currentCurrency.set(mCurrencyMap[currencyAbriviation])
        }

    }

    companion object {

        @BindingAdapter("app:onFocusChange")
        fun onFocusChange(text: EditText, listener: View.OnFocusChangeListener) {
            text.onFocusChangeListener = listener
        }
    }

}
