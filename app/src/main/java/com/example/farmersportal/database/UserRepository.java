package com.example.farmersportal.database;

import android.app.Application;

public class UserRepository {
    private final UserDao userDao;

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
}
