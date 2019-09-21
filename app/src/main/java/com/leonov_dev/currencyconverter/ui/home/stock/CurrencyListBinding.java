package com.leonov_dev.currencyconverter.ui.home.stock;

import androidx.databinding.BindingAdapter;

import android.widget.ImageView;
import android.widget.ListView;

import com.leonov_dev.currencyconverter.data.CurrencyReplacement;

import java.util.List;

public class CurrencyListBinding {

    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<CurrencyReplacement> items) {
        CurrencyReplacementAdapter adapter = (CurrencyReplacementAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter("app:flagLogo")
    public static void setFlagLogo(ImageView view, int resId){
        view.setImageResource(resId);
    }

}
