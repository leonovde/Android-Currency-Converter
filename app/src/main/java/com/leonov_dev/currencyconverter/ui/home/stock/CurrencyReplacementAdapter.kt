package com.leonov_dev.currencyconverter.ui.home.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.leonov_dev.currencyconverter.data.CurrencyReplacement

class CurrencyReplacementAdapter(var currencies: List<CurrencyReplacement>) : BaseAdapter() {

    override fun getCount(): Int = currencies.size

    override fun getItem(position: Int): Any {
        return currencies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(null, null, false)
    }
}
