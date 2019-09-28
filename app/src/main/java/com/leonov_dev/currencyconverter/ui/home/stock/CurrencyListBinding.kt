package com.leonov_dev.currencyconverter.ui.home.stock

import androidx.databinding.BindingAdapter

import android.widget.ImageView
import android.widget.ListView

import com.leonov_dev.currencyconverter.data.CurrencyReplacement

object CurrencyListBinding {

    @BindingAdapter("app:items")
    fun setItems(listView: ListView, items: List<CurrencyReplacement>) {
        val adapter = listView.adapter as CurrencyReplacementAdapter?
        adapter?.replaceData(items)
    }

    @BindingAdapter("app:flagLogo")
    fun setFlagLogo(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }
}
