package com.liyu.huahui;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by liyu on 2017/3/7.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
