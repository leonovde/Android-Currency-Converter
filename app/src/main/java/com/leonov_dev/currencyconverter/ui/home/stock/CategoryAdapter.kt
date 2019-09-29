package com.leonov_dev.currencyconverter.ui.home.stock

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.leonov_dev.currencyconverter.R
import com.leonov_dev.currencyconverter.ui.home.converter.ConverterFragment

class CategoryAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            StockFragment()
        } else {
            ConverterFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            mContext.getString(R.string.StockTitle)
        } else {
            mContext.getString(R.string.ConverterTitle)
        }
    }

    override fun getCount(): Int = 2
}
