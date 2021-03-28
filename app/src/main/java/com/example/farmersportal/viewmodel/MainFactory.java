package com.example.farmersportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application application;

    public MainFactory(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == UserViewModel.class)
            return (T) new UserViewModel(application);
        else if (modelClass == ProductViewModel.class)
            return (T) new ProductViewModel(application);
        return super.create(modelClass);
    }
}
