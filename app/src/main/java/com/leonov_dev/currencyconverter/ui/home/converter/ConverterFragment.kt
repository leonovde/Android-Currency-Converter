package com.leonov_dev.currencyconverter.ui.home.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel
import com.leonov_dev.currencyconverter.databinding.FragmentConverterBinding
import com.leonov_dev.currencyconverter.ui.home.MainActivity

import org.json.JSONObject

class ConverterFragment : Fragment() {

    private val quantityOfCurrency = 0
    private val priceOfCurrency = 0.000

    private val currenciesJSON: JSONObject? = null

    private val mHowMuchMyr: TextView? = null
    private val mHowMuchOthers: TextView? = null
    private val otherCurrencyNominalPrice: Double = 0.00

    private var mStockViewModel: StockConverterViewModel? = null

    private lateinit var mConverterBinding: FragmentConverterBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mConverterBinding = FragmentConverterBinding.inflate(inflater, container, false)
        mStockViewModel = MainActivity.obtainViewModel(requireActivity())
        mConverterBinding.viewmodel = mStockViewModel
        setHasOptionsMenu(true)
        return mConverterBinding.getRoot()
    }


    private fun setPriceNotes(currencyName: String) {
        val oneMyrIs = "1 MYR =  $currencyName"
        val oneOtherIs = (quantityOfCurrency.toString() + " " + currencyName + " = "
            + priceOfCurrency + " MYR")
        mHowMuchMyr!!.text = oneMyrIs
        mHowMuchOthers!!.text = oneOtherIs
    }

}
