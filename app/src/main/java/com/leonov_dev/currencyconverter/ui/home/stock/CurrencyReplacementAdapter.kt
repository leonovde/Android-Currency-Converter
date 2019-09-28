package com.leonov_dev.currencyconverter.ui.home.stock

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.leonov_dev.currencyconverter.data.CurrencyReplacement
import com.leonov_dev.currencyconverter.databinding.CurrencyItemTwoBinding
import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel

class CurrencyReplacementAdapter(currencies: List<CurrencyReplacement>,
                                 private val mStockViewModel: StockConverterViewModel) : BaseAdapter() {

    private var mCurrencies: List<CurrencyReplacement>? = null

    init {
        setList(currencies)
    }

    private fun setList(currencies: List<CurrencyReplacement>) {
        mCurrencies = currencies
        notifyDataSetChanged()
    }

    fun replaceData(currencies: List<CurrencyReplacement>) {
        setList(currencies)
    }


    override fun getCount(): Int {
        return if (mCurrencies != null) mCurrencies!!.size else 0
    }

    override fun getItem(position: Int): Any {
        return mCurrencies!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val binding: CurrencyItemTwoBinding?
        if (view == null) {
            val inflater = LayoutInflater.from(parent.context)
            binding = CurrencyItemTwoBinding.inflate(inflater, parent, false)
        } else {
            binding = DataBindingUtil.getBinding(view)
        }
        binding!!.currency = mCurrencies!![position]
        binding.executePendingBindings()
        return binding.root
    }
}
