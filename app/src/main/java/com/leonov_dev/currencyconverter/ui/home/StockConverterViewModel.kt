package com.leonov_dev.currencyconverter.ui.home

import android.app.Application
import android.content.Context

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.data.CurrencyReplacement
import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository
import com.leonov_dev.currencyconverter.model.RatesGetModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap

class StockConverterViewModel(application: Application,
                              private val currenciesRepository: CurrenciesRepository) : AndroidViewModel(application) {

    private var context: Context = application.applicationContext // AppContext

    private val LOG_TAG = StockConverterViewModel::class.java.simpleName

    val items: ObservableList<CurrencyReplacement> = ObservableArrayList()

    val empty = ObservableBoolean(true)

    //Converter activity
    var myrAmount = ObservableField<String>()
    var otherAmount = ObservableField<String>()
    var currentCurrency = ObservableField<CurrencyReplacement>()
    private val mListCurrencies = ObservableArrayList<String>()
    private val mCurrencyMap = HashMap<String, CurrencyReplacement>()

    val ratesLiveData = MutableLiveData<RatesGetModel>()

    private var isMyrFocused = false
    private var isOtherFocused = false
    private val MYR_FLAG = 10
    private val OTHERS_FLAG = 20

    init {
        loadRemoteData()
    }

    fun loadRemoteData() {
        viewModelScope.launch {
            ratesLiveData.value = withContext(Dispatchers.IO) {
                currenciesRepository.fetchRates()
            }.await()
            val int = 1
        }
    }

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
}
