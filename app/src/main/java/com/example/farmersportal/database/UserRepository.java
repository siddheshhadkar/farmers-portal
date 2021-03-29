package com.example.farmersportal.database;

import android.app.Application;

public class UserRepository {
    private final UserDao userDao;
    private ValueCheckCallback callback;

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

    public void valuesExist(String enteredEmail, String enteredPhone){
        MainDatabase.dbWriteExecutor.execute(()->{
            callback.valueCheck(userDao.emailExists(enteredEmail), userDao.phoneExists(enteredPhone));
        });
    }

    public void setCheckListener(ValueCheckCallback callback) {
        this.callback = callback;
    }

    public interface ValueCheckCallback {
        void valueCheck(boolean emailExists, boolean phoneExists);
    }
}
