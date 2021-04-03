package com.example.farmersportal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farmersportal.database.User;
import com.example.farmersportal.database.UserRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public void emailExists(String enteredEmail) {
        repository.emailExists(enteredEmail);
    }

    public void checkPassword(String enteredEmail, String enteredPassword) {
        repository.checkPassword(enteredEmail, enteredPassword);
    }

    public User getUser(String email) throws ExecutionException, InterruptedException {
        return repository.getUser(email);
    }

    public User getUser(int id) throws ExecutionException, InterruptedException {
        return repository.getUser(id);
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUsers();
    }

    public UserRepository getRepository() {
        return repository;
    }
}
