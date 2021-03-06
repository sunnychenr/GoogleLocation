package com.chenr.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by ChenR on 2016/11/16.
 */

public class App extends Application {

    public static String SystemLanguage = "";
    public static String AppKey = "AIzaSyAC7f8wFBelDxjr6GW-WCLb-m8qqsuyQx4";

    public static Context applicationContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        SystemLanguage = getResources().getConfiguration().locale.getLanguage();
    }
}
