package com.leonov_dev.currencyconverter.ui.home.stock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leonov_dev.currencyconverter.databinding.ActivityStockBinding
import com.leonov_dev.currencyconverter.ui.home.MainActivity
import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel


import java.util.ArrayList


class StockFragment : Fragment() {

    private lateinit var stockViewModel: StockConverterViewModel
    private lateinit var mStockBinding: ActivityStockBinding
    private var mCurrencyAdapter: CurrencyReplacementAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mStockBinding = ActivityStockBinding.inflate(inflater, container, false)
        stockViewModel = MainActivity.obtainViewModel(activity!!)
        mStockBinding.viewmodel = stockViewModel
        setHasOptionsMenu(true)
        return mStockBinding.root
    }

    override fun onResume() {
        super.onResume()
        stockViewModel.loadRemoteData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val listView = mStockBinding.list
        mCurrencyAdapter = CurrencyReplacementAdapter(
            ArrayList(0),
            stockViewModel
        )
        listView.adapter = mCurrencyAdapter
    }
}