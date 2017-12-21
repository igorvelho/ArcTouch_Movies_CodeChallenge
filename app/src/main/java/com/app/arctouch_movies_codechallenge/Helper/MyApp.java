package com.app.arctouch_movies_codechallenge.Helper;

import android.app.Application;
import android.content.Context;

/**
 * Created by igorv on 11/12/2017.
 */

public class MyApp extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApp.context;
    }
}