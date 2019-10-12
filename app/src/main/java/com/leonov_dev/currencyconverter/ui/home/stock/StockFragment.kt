package com.leonov_dev.currencyconverter.ui.home.stock

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leonov_dev.currencyconverter.R

import com.leonov_dev.currencyconverter.ui.home.MainActivity
import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel


import java.util.ArrayList

class StockFragment : Fragment() {

    private lateinit var stockViewModel: StockConverterViewModel
    private var currencyAdapter: CurrencyReplacementAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_stock, container, false)
        stockViewModel = MainActivity.obtainViewModel(requireActivity())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyAdapter = CurrencyReplacementAdapter(ArrayList(0))
    }

    override fun onResume() {
        super.onResume()
        stockViewModel.loadRemoteData()
    }
}