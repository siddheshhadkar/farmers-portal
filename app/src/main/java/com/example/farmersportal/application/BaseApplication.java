package com.example.farmersportal.application;

import android.app.Application;

public class BaseApplication extends Application {
    private static BaseApplication application;

    public static BaseApplication getApplicationInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
