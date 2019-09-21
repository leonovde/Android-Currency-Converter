package com.leonov_dev.currencyconverter.ui.home.converter;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel;
import com.leonov_dev.currencyconverter.databinding.FragmentConverterBinding;
import com.leonov_dev.currencyconverter.ui.home.MainActivity;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConverterFragment extends Fragment {


    private int quantityOfCurrency = 0;
    private double priceOfCurrency = 0.000;

    private JSONObject currenciesJSON;

    private TextView mHowMuchMyr;
    private TextView mHowMuchOthers;
    private double otherCurrencyNominalPrice;


    public final String LOG_TAG = this.getClass().getSimpleName();


    public ConverterFragment() {
        // Required empty public constructor
    }

    //-----------------CHANGES-------------------
    private StockConverterViewModel mStockViewModel;

    private FragmentConverterBinding mConverterBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mConverterBinding = FragmentConverterBinding.inflate(inflater, container, false);

        mStockViewModel = MainActivity.obtainViewModel(getActivity());

        mConverterBinding.setViewmodel(mStockViewModel);

        setHasOptionsMenu(true);

        return mConverterBinding.getRoot();
    }


    private void setPriceNotes(String currencyName){
        String oneMyrIs = "1 MYR = " /*formatPrice(otherCurrencyNominalPrice)*/ + " " + currencyName;
        String oneOtherIs = quantityOfCurrency + " " + currencyName + " = "
                + priceOfCurrency + " MYR" ;
        mHowMuchMyr.setText(oneMyrIs);
        mHowMuchOthers.setText(oneOtherIs);
    }

}
