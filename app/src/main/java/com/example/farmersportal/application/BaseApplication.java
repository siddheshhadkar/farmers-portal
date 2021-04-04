package com.example.farmersportal.application;

import android.app.Application;

import com.example.farmersportal.database.User;

public class BaseApplication extends Application {
    private static BaseApplication application;
    private User sessionUser;

    public static BaseApplication getApplicationInstance() {
        if (application == null){
            application = new BaseApplication();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}
