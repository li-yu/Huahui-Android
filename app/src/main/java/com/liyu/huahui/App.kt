package com.liyu.huahui;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;

import org.litepal.LitePal;

/**
 * Created by liyu on 2017/3/7.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(this);
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
