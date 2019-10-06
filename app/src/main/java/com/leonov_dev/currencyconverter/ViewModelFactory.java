package com.leonov_dev.currencyconverter;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository;
import com.leonov_dev.currencyconverter.data.source.local.LocalDataSource;
import com.leonov_dev.currencyconverter.data.source.local.CurrencyDatabase;
import com.leonov_dev.currencyconverter.data.source.remote.RemoteDataSource;
import com.leonov_dev.currencyconverter.ui.home.StockConverterViewModel;
import com.leonov_dev.currencyconverter.utils.AppExecutors;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final CurrenciesRepository mTasksRepository;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {

                    CurrencyDatabase database =
                            CurrencyDatabase.getInstance(application.getApplicationContext());

                    LocalDataSource localDataSource =
                            new LocalDataSource(
                                    new AppExecutors(),
                                    database.taskDao());

                    RemoteDataSource remoteDataSource = new RemoteDataSource();

                    CurrenciesRepository repository =
                            new CurrenciesRepository(localDataSource, remoteDataSource);

                    INSTANCE = new ViewModelFactory(application,repository);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, CurrenciesRepository repository) {
        mApplication = application;
        mTasksRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StockConverterViewModel.class)){
            return (T) new StockConverterViewModel(mApplication, mTasksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
