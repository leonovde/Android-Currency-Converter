package com.leonov_dev.currencyconverter.ui.home.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.leonov_dev.currencyconverter.R

import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel
import com.leonov_dev.currencyconverter.model.RatesGetModel
import com.leonov_dev.currencyconverter.ui.home.MainActivity

import org.json.JSONObject

class ConverterFragment : Fragment() {

    private val quantityOfCurrency = 0
    private val priceOfCurrency = 0.000

    private var mStockViewModel: StockConverterViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)
        mStockViewModel = MainActivity.obtainViewModel(requireActivity())
        return view
    }

    override fun onResume() {
        super.onResume()
        mStockViewModel?.ratesLiveData?.observe(this, Observer<RatesGetModel> {})
    }

    private fun setPriceNotes(currencyName: String) {
        val oneMyrIs = "1 MYR =  $currencyName"
        val oneOtherIs = (quantityOfCurrency.toString() + " " + currencyName + " = "
                + priceOfCurrency + " MYR")
    }
}
