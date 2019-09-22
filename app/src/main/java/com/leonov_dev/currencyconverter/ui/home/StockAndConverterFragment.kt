package com.leonov_dev.currencyconverter.ui.home

import android.os.Bundle
import com.leonov_dev.currencyconverter.ui.home.stock.CategoryAdapter
import com.leonov_dev.currencyconverter.R

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stock_and_converter.*

class StockAndConverterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_stock_and_converter, container, false)
        val adapter = CategoryAdapter(requireContext(), childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        return rootView
    }
}
