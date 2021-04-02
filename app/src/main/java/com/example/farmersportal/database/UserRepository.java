package com.example.farmersportal.database;

import android.app.Application;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepository {
    private final UserDao userDao;
    private SignUpCheckCallback signUpCheckCallback;
    private LogInCheckCallback logInCheckCallback;

    public UserRepository(Application application) {
        MainDatabase mainDatabase = MainDatabase.getInstance(application);
        userDao = mainDatabase.userDao();
    }

    public void insert(User user) {
        MainDatabase.dbWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        MainDatabase.dbWriteExecutor.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        MainDatabase.dbWriteExecutor.execute(() -> userDao.delete(user));
    }

    public void deleteAll() {
        MainDatabase.dbWriteExecutor.execute(userDao::deleteAll);
    }

    public void valuesExist(String enteredEmail, String enteredPhone) {
        MainDatabase.dbWriteExecutor.execute(() -> signUpCheckCallback.valueCheck(userDao.emailExists(enteredEmail), userDao.phoneExists(enteredPhone)));
    }

    public void emailExists(String enteredEmail) {
        MainDatabase.dbWriteExecutor.execute(() -> logInCheckCallback.emailCheck(userDao.emailExists(enteredEmail)));
    }

    public void checkPassword(String enteredEmail, String enteredPassword) {
        MainDatabase.dbWriteExecutor.execute(() -> logInCheckCallback.accountValidate(userDao.accountExists(enteredEmail, enteredPassword)));
    }

    public User getUser(String email) throws ExecutionException, InterruptedException {
        Callable<User> callable = () -> userDao.getUser(email);
        Future<User> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    public void setSignUpCheckListener(SignUpCheckCallback callback) {
        signUpCheckCallback = callback;
    }

    public void setLogInCheckListener(LogInCheckCallback callback) {
        logInCheckCallback = callback;
    }

    public interface SignUpCheckCallback {
        void valueCheck(boolean emailExists, boolean phoneExists);
    }

    public interface LogInCheckCallback {
        void emailCheck(boolean emailExists);

        void accountValidate(boolean validated);
    }
}
