package com.leonov_dev.currencyconverter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new StockFragment();
        }else {
            return new ConverterFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.StockTitle);
        }else {
            return mContext.getString(R.string.ConverterTitle);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
