package com.leonov_dev.currencyconverter.ui.home.stock;

import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;
import com.leonov_dev.currencyconverter.databinding.CurrencyItemTwoBinding;
import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel;

import java.util.List;

public class CurrencyReplacementAdapter extends BaseAdapter {

    private final StockConverterViewModel mStockViewModel;

    private List<CurrencyReplacement> mCurrencies;

    public CurrencyReplacementAdapter(List<CurrencyReplacement> currencies,
                                      StockConverterViewModel viewModel){
        mStockViewModel = viewModel;
        setList(currencies);
    }

    private void setList(List<CurrencyReplacement> currencies) {
        mCurrencies = currencies;
        notifyDataSetChanged();
    }

    public void replaceData(List<CurrencyReplacement> currencies) {
        setList(currencies);
    }


    @Override
    public int getCount() {
        return mCurrencies != null ? mCurrencies.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mCurrencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CurrencyItemTwoBinding binding;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = CurrencyItemTwoBinding.inflate(inflater, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }
        binding.setCurrency(mCurrencies.get(position));
        binding.executePendingBindings();
        return binding.getRoot();
    }
}
