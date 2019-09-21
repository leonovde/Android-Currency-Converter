package com.leonov_dev.currencyconverter.ui.home;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.leonov_dev.currencyconverter.ui.home.stock.CategoryAdapter;
import com.leonov_dev.currencyconverter.R;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StockAndConverterFragment extends Fragment {

    private ViewPager viewPager;

    private final String LOG_TAG = this.getClass().getSimpleName();

    public StockAndConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock_and_converter, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_viewpager);
        CategoryAdapter adapter = new CategoryAdapter(getActivity().getApplicationContext(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.fragment_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

}
