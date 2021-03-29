package com.example.farmersportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.farmersportal.database.User;
import com.example.farmersportal.database.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void valuesExist(String enteredEmail, String enteredPhone) {
        repository.valuesExist(enteredEmail, enteredPhone);
    }

    public UserRepository getRepository() {
        return repository;
    }
}
