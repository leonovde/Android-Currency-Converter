package com.leonov_dev.currencyconverter;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Dao;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.leonov_dev.currencyconverter.data.source.CurrenciesRepository;
import com.leonov_dev.currencyconverter.data.source.local.CurrenciesLocalDataSource;
import com.leonov_dev.currencyconverter.data.source.local.CurrencyDatabase;
import com.leonov_dev.currencyconverter.data.source.remote.CurrenciesRemoteDataSource;
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

                    CurrenciesLocalDataSource localDataSource =
                            CurrenciesLocalDataSource.getInstance(
                                    new AppExecutors(),
                                    database.taskDao());

                    CurrenciesRemoteDataSource remoteDataSource =
                            CurrenciesRemoteDataSource.getInstance(new AppExecutors());

                    CurrenciesRepository repository =
                            CurrenciesRepository.getInstance(localDataSource, remoteDataSource);

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
