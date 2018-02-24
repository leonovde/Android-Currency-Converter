package com.leonov_dev.currencyconverter;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockAndConverterFragment extends Fragment {

    private ViewPager viewPager;


    public StockAndConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stock_and_converter, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.fragment_viewpager);

        CategoryAdapter adapter = new CategoryAdapter(getActivity().getApplicationContext(), getChildFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.fragment_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

}
